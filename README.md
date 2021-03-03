# 小马哥训练营 作业项目

## 项目介绍

> 一个简单的web项目

### 操作手册

- 打包
> 到 user-platform 目路下，执行下面命令

```bash
mvn clean package -U
```

- 启动
> 打包完成之后复制 `ning-user-web` 项目的target目路绝对路径: packagePath=你本地的target目路的绝对路径

```bash
# start
java -jar ${packagePath}\ning-user-web-1.0-SNAPSHOT-war-exec.jar
# debug start
java -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=y -jar ${packagePath}\ning-user-web-1.0-SNAPSHOT-war-exec.jar
``` 

### 功能测试

> 注册 http://localhost:8080/register

### 单元测试

> 覆盖了UserService所有方法的测试类 com.ning.geekbang.user.web.service.impl.UserServiceImplTest
