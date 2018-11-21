# 项目依赖的服务

项目依赖于 MySQL、RabbitMQ、Redis 来提供服务，请确保在项目启动时先启动了上述三个组件，并且正确配置。本文件夹下还提供了 `docker-compose.yml` 用于简化 Docker 方式下组件的启动。

## 直接运行 Docker Compose

首次运行：

```bash
docker-compose up -d
```

停止：

```bash
docker-compose stop
```

之后运行：

```bash
docker-compose start
```

删除：

```bash
docker-compose rm
```

运行之后还需要修改[邮箱相关配置](#邮箱)，如果不想使用 Docker Compose，则需要手动配置 MySQL、RabbitMQ、Redis。

## MySQL

主机：本机（localhost），端口 3306。初始化数据库（在本目录下运行）：

```bash
mysql -u root -e 'CREATE DATABASE target CHARACTER SET utf8mb4;'
mysql -u root -D target < src/main/resources/data.sql
mysql -u root -e 'CREATE USER admin@"%" identified by "admin";'
mysql -u root -e 'GRANT ALL PRIVILEGES ON target.* to admin@"%" WITH GRANT OPTION;'
```

## RabbitMQ

建议以 Docker 方式运行 RabbitMQ，可以简化配置流程：

```bash
$ docker run --name rabbitmq \
-p 5672:5672 \
-p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=user \
-e RABBITMQ_DEFAULT_PASS=user \
-d rabbitmq:3-management
```

## Redis

主机：本机（localhost），端口：3306。

## 邮箱

因为涉及到利用邮箱重置用户密码，所以需要发送邮件，如果你使用的是腾讯企业邮箱，则只需配置两个环境变量：

```bash
export TEST_MAIL_USERNAME=YourUsername
export TEST_MAIL_PASSWORD=YourPassword
```

如果使用的不是腾讯企业邮箱，则需修改 `src/main/resources/application.properties` 中 `spring.mail` 开头的一系列配置。
