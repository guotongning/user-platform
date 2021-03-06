package com.ning.web.mvc.servlet;

import com.ning.geekbang.ioc.context.ComponentContext;
import com.ning.web.mvc.controller.Controller;
import com.ning.web.mvc.controller.PageController;
import com.ning.web.mvc.controller.RestController;
import com.ning.web.mvc.exception.WebMvcException;
import com.ning.web.mvc.handler.*;
import com.ning.web.mvc.response.Result;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

import static com.ning.web.mvc.utils.RequestMappingUtils.handleRequestMapping;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.substringAfter;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 16:20
 * @Descreption: 处理映射和Controller具体实现的调用
 */
public class WebControllerServlet extends HttpServlet {


    private ServletContext servletContext;

    private ComponentContext webComponentContext;

    private PageControllerHandler pageControllerHandler;

    private RestControllerHandler restControllerHandler;
    /**
     * 存储 requestMapping --> Controller 的映射
     */
    private Map<String, Controller> pathControllerMapping = new HashMap<>();

    /**
     * 存储 requestMapping --> method 的映射
     */
    private Map<String, HandlerMethod> pathHandlerMethodMapping = new HashMap<>();


    private Set<Controller> registeredControllers = new HashSet<>();
    /**
     * 一个控制层方法默认支持的请求方式
     */
    private static final Set<String> DEFAULT_SUPPORTED_HTTP_METHODS = new LinkedHashSet<>(asList(HttpMethod.GET, HttpMethod.POST,
            HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));

    public void init() {
        servletContext = this.getServletContext();
        webComponentContext = (ComponentContext) servletContext.getAttribute(ComponentContext.CONTEXT_NAME);
        initPageControllerHandler();
        initRestControllerHandler();
        initControllerInfo();
        injectWebComponents();
    }

    private void injectWebComponents() {
        registeredControllers.stream().forEach(controller -> {
            Class<? extends Controller> controllerClass = controller.getClass();
            injectWebComponents(controller, controllerClass);
        });
    }

    private void injectWebComponents(Controller controller, Class<? extends Controller> clazz) {
        Stream.of(clazz.getDeclaredFields()).filter(field -> {
            int mods = field.getModifiers();
            return !Modifier.isStatic(mods) &&
                    field.isAnnotationPresent(Resource.class);
        }).forEach(field -> {
            Resource annotation = field.getAnnotation(Resource.class);
            String name = annotation.name();
            Object component = webComponentContext.getComponent(name);
            field.setAccessible(true);
            try {
                field.set(controller, component);
            } catch (IllegalAccessException e) {
                throw new WebMvcException(String.format("can not init controller[%s] field[%s]", clazz, field.getName()));
            }
        });
    }

    /**
     * 初始化pageController的请求处理方式
     */
    private void initPageControllerHandler() {
        servletContext.log("Init PageControllerHandler Start");
        ServiceLoader<PageControllerHandler> pageControllerHandlers = ServiceLoader.load(PageControllerHandler.class);
        Iterator<PageControllerHandler> iterator = pageControllerHandlers.iterator();
        if (iterator.hasNext()) {
            //多个只有第一个生效
            pageControllerHandler = iterator.next();
        } else {
            pageControllerHandler = new DefaultPageControllerHandler();
        }
        servletContext.log(String.format("PageControllerHandler has been set: [%s]", pageControllerHandler.getClass().getSimpleName()));
    }

    private void initRestControllerHandler() {
        servletContext.log("Init RestControllerHandler Start");
        ServiceLoader<RestControllerHandler> restControllerHandlers = ServiceLoader.load(RestControllerHandler.class);
        Iterator<RestControllerHandler> iterator = restControllerHandlers.iterator();
        if (iterator.hasNext()) {
            //多个只有第一个生效
            restControllerHandler = iterator.next();
        } else {
            restControllerHandler = new DefaultRestControllerHandler();
        }
        servletContext.log(String.format("RestControllerHandler has been set: [%s]", restControllerHandler.getClass().getSimpleName()));
    }


    private void initControllerInfo() {
        servletContext.log("Init RequestMapping Start");
        for (Controller implController : ServiceLoader.load(Controller.class)) {
            Class<? extends Controller> controllerClass = implController.getClass();
            Path reqPathOnClass = controllerClass.getAnnotation(Path.class);
            StringBuilder requestMappingBuilder = new StringBuilder();
            if (reqPathOnClass != null) {
                requestMappingBuilder.append(handleRequestMapping(reqPathOnClass.value()));
            }
            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                Path pathOnMethod = method.getAnnotation(Path.class);
                requestMappingBuilder.append(handleRequestMapping(pathOnMethod == null ? "" : pathOnMethod.value()));
                if (requestMappingBuilder.length() == 0) {
                    throw new WebMvcException(String.format("method：[%s], request mapping must be set! use @Path on class or method to set the request mapping.", method.getName()));
                }
                Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
                pathHandlerMethodMapping.put(requestMappingBuilder.toString(),
                        new HandlerMethod(requestMappingBuilder.toString(), method, supportedHttpMethods));
            }
            pathControllerMapping.put(requestMappingBuilder.toString(), implController);
            registeredControllers.add(implController);
            servletContext.log(String.format("Loaded RequestMapping: [%s]", requestMappingBuilder.toString()));
        }
    }

    private Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();
        for (Annotation annotationOnMethod : method.getAnnotations()) {
            HttpMethod httpMethod = annotationOnMethod.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }
        if (supportedHttpMethods.isEmpty()) {
            return DEFAULT_SUPPORTED_HTTP_METHODS;
        }
        return supportedHttpMethods;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String prefixPath = request.getContextPath();
        String requestMappingPath = substringAfter(requestURI,
                StringUtils.replace(prefixPath, "//", "/"));
        Controller controller = pathControllerMapping.get(requestMappingPath);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        HandlerMethod handlerMethod = pathHandlerMethodMapping.get(requestMappingPath);
        if (handlerMethod == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String httpMethod = request.getMethod();
        if (!handlerMethod.getSupprotedHttpMethods().contains(httpMethod)) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }
        if (controller instanceof PageController) {
            //页面跳转逻辑
            pageControllerHandler.handle(request, response, controller);
        } else if (controller instanceof RestController) {
            //TODO 业务处理逻辑
            Result result = restControllerHandler.handle(request, response, controller);
            response.getWriter().write(JSON.toJSONString(result));
        }
    }

}
