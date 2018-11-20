# 项目依赖的服务

## MySQL

```bash
$ docker run --name mysql \
-e TZ=Asia/Shanghai \
-e MYSQL_ROOT_PASSWORD=mysqlroot \
-p 3306:3306 \
-d mysql:8
```

## RabbitMQ

```bash
$ docker run --name rabbitmq \
-p 5672:5672 \
-p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=user \
-e RABBITMQ_DEFAULT_PASS=user \
-d rabbitmq:3-management
```

## 构建 Docker 镜像

```bash
mvn clean package -DskipTests docker:removeImage docker:build
```
