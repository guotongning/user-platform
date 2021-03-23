package com.ning.geekbang.ioc.context;

import com.ning.commons.function.ThrowableAction;
import com.ning.commons.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * @Author: nicholas
 * @Date: 2021/3/5 23:47
 * @Descreption: 组件上下文 这个实现是能有一个实例。应该再启动的时候做检查。但是我没做。
 */
public class ComponentContext {

    public static final String CONTEXT_NAME = "ComponentContext";

    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

    public static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static ServletContext servletContext;

    private MBeanServer mBeanServer;

    private Context envContext;

    private ClassLoader classLoader;

    private Map<String, Object> componentsMap = new LinkedHashMap<>();
    private Map<String, Object> mBeanMap = new LinkedHashMap<>();


    /**
     * 获取 ComponentContext
     *
     * @return
     */
    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

    /**
     * 这里初始化除Controller以外的所有Bean
     */
    public void init(ServletContext servletContext) {
        ComponentContext.servletContext = servletContext;
        servletContext.setAttribute(CONTEXT_NAME, this);
        this.classLoader = servletContext.getClassLoader();
        mBeanServer = ManagementFactory.getPlatformMBeanServer();
        //初始化环境上下文
        initEnvContext();
        //实例化Bean
        instantiateComponents();
        //初始化Bean
        initializeComponents();
        //实例化MBean
        instantiateMBean();
        //初始化MBean
        initializeMBean();
    }

    /**
     * 这里的实例化 = component 的 实例化 + 初始化
     */
    private void instantiateMBean() {
        //TODO 以后在做加载吧。就先这么干了。
//        mBeanMap.put("User", new UserManager(new User()));
    }

    private void initializeMBean() {
        mBeanMap.forEach((preName, mBean) -> {
            try {
                ObjectName name = new ObjectName("jolokia:name=" + preName);
                mBeanServer.registerMBean(mBean, name);
            } catch (Exception e) {
                throw new RuntimeException("jolokia MBean init fail! name=" + preName, e);
            }
        });
    }

    public void destroy() {
        componentsMap.values().forEach(component -> {
            Class<?> componentClass = component.getClass();
            //销毁
            processPreDestroy(component, componentClass);
        });
    }

    private void initEnvContext() {
        if (this.envContext != null) {
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            if (context != null) {
                ThrowableAction.execute(context::close);
            }
        }

    }

    /**
     * 初始化组件 - 标准 Commons Annotation 生命周期
     * <ol>
     * <li>注入阶段 - {@link Resource}</li>
     * <li>初始化阶段 - {@link PostConstruct}</li>
     * <li>销毁阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    private void initializeComponents() {
        componentsMap.values().forEach(component -> {
            Class<?> componentClass = component.getClass();
            //注入
            injectComponents(component, componentClass);
            //初始化
            processPostConstruct(component, componentClass);
        });
    }

    /**
     * 注入组件
     *
     * @param component      组件
     * @param componentClass 组件的Class
     */
    private void injectComponents(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields()).filter(field -> {
            int mods = field.getModifiers();
            return !Modifier.isStatic(mods) &&
                    field.isAnnotationPresent(Resource.class);
        }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String name = resource.name();
            Object injectObject = lookupComponent(name);
            field.setAccessible(true);
            try {
                field.set(component, injectObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(componentClass + " 注入失败！ resource = " + name, e);
            }
        });

    }

    /**
     * 初始化组件
     *
     * @param component      组件
     * @param componentClass 组件的Class
     */
    private void processPostConstruct(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods()).filter(method ->
                !Modifier.isStatic(method.getModifiers()) &&
                        method.getParameterCount() == 0 &&
                        method.isAnnotationPresent(PostConstruct.class)
        ).forEach(method -> {
            try {
                method.invoke(component);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(componentClass + " 初始化失败！ method = " + method.getName(), e);
            }
        });
    }

    /**
     * 销毁组件
     *
     * @param component      组件
     * @param componentClass 组件的Class
     */
    private void processPreDestroy(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods()).filter(method ->
                !Modifier.isStatic(method.getModifiers()) &&
                        method.getParameterCount() == 0 &&
                        method.isAnnotationPresent(PreDestroy.class)

        ).forEach(method -> {
            try {
                method.invoke(component);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(componentClass + " 销毁失败！ method = " + method.getName(), e);
            }
        });
    }

    /**
     * 实例化
     */
    private void instantiateComponents() {
        List<String> componentsNames = listAllComponentNames();
        componentsNames.forEach(name -> componentsMap.put(name, lookupComponent(name)));
    }


    /**
     * 遍历所有组件的名称
     *
     * @return 所有组件的名称
     */
    private List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    /**
     * 根据路径便利该路径下的组件的名称
     *
     * @param name 路径
     * @return 路径下组件名称集合
     */
    protected List<String> listComponentNames(String name) {
        return executeInContext(context -> {
            NamingEnumeration<NameClassPair> e = executeInContext(context, ctx -> ctx.list(name), true);
            if (e == null) {
                return Collections.emptyList();
            }
            List<String> names = new LinkedList<>();
            while (e.hasMoreElements()) {
                NameClassPair element = e.nextElement();
                String className = element.getClassName();
                Class<?> elementClass = classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(elementClass)) {
                    names.addAll(listComponentNames(element.getName()));
                } else {
                    String fullName = name.startsWith("/") ?
                            element.getName() : name + "/" + element.getName();
                    names.add(fullName);
                }
            }
            return names;
        });
    }

    /**
     * 根据组件的名称通过context加载组件
     *
     * @param name 组件名称
     * @param <C>  function 返回值类型
     * @return 组件
     */
    protected <C> C lookupComponent(String name) {
        return executeInContext(context -> (C) context.lookup(name));
    }


    /**
     * 根据组件的名称通过context加载组件
     *
     * @param function 加载组件的方法
     * @param <R>      function 返回值类型
     * @return 组件
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function) {
        return executeInContext(function, false);
    }

    /**
     * 通过context加载组件
     *
     * @param function         加载组件的方法
     * @param ignoredException 是否忽略加载组件时出现的异常
     * @param <R>              function 返回值类型
     * @return 组件
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.envContext, function, ignoredException);
    }

    /**
     * 加载组件
     *
     * @param context          环境上下文
     * @param function         加载组件的方法
     * @param ignoredException 是否忽略加载组件时的异常
     * @param <R>              function 返回值类型
     * @return 组件
     */
    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function, boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 通过名称进行依赖查找
     *
     * @param name 组件的名称
     * @param <C>  组件的类型
     * @return
     */
    public <C> C getComponent(String name) {
        return (C) componentsMap.get(name);
    }


}
