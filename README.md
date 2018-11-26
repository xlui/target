# Target

[![Build Status](https://travis-ci.org/xlui/target.svg?branch=master)](https://travis-ci.org/xlui/target)
[![GitHub](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/xlui/target)

一个简单的打卡应用。

## 项目构思

本来已经有了 `小目标` 这样的打卡 App，有以下优点：

1. 预设常用目标，添加时可直接点选，同时也支持自定义目标
1. 提供目标统计功能，可以统计当前月份打卡情况，并可查看历史月份情况
1. 以时间轴记录打卡内容、时间，可以看到自己的蜕变
1. 提供打卡周报，统计一周打卡情况

但是有几点用的不舒服：

1. 补签，如果因为意外忘记打卡，补签卡需要借助推广该 App 来获取
1. 提醒，不能在当天某目标过期前有效提醒
1. 广告，免费版存在垃圾广告
1. 社交，该 App 随着版本的更新加入了垃圾社交功能（~~无用~~）

所以我需要一个自己定制的打卡应用，针对以上点：

1. 补签，提供补签功能，但是补签的标识与有效时间内打卡不同，并加入统计
1. 提醒，提供任务栏通知、短信、邮件三种方式（可选）
1. 广告，不考虑
1. 社交，仅限于打卡排行，无其他形式互动

## 功能

- 注册、登录、验证、忘记密码
- 创建目标、更新目标、删除目标
- 打卡、补签
- 当月目标统计、往月目标统计
- 成长记录、打卡周报
- 打卡排行

## 运行

首先拉取项目：

```bash
git clone https://github.com/xlui/target.git
cd target/
```

### Server

启动服务器端所需容器：

```bash
cd server/
# 首次启动运行下面的命令
docker-compose up -d
# 非首次启动在目录下运行下面的命令
docker-compose start
```

构建、运行：

```bash
mvn clean package -DskipTests
java -jar target/target-0.0.1.jar
```

这样服务器就启动了，这是手动启动的方式，推荐使用 IntelliJ IDEA 打开项目然后运行。

### Frontend

```bash
cd web/
yarn install
npm run dev
```

浏览器访问 http://127.0.0.1:8081 即可。

# License

MIT
