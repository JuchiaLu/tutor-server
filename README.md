## 基于Spring Cloud全家桶的分布式微服务C2C模式RESTful风格前后端完全分离的在线家教预约平台

### (一) 项目介绍

​		在线家教预约平台采用C2C运营模式，Spring Cloud作为后端基础开发框架，Vue.js作为前端基础开发框架，以微服务架构进行设计，前后端完全分离，使用JWT作为无状态解决方案，提供RESTful风格的API、后台动态权限管理、第三方登录以及单点登录功能。系统可以为家长或学员快速方便的的根据自身条件发布家教需求，教员可以按需预约发布的需求，同时还实现了评价、支付、消息、为家教服务做担保等功能。

![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/02.png)	

### (二) 技术选型

主要项目依赖

1. 基础框架: Spring, Spring MVC, Spring Boot, Spring Cloud 
2. 安全框架: Spring Security, Spring Social, Spring Cloud Oauth2
3. 网关：Spring Cloud Gateway
4. ORM框架: Mybatis Plus
5. 远程服务调用: Feign
6. API文档：Swagger2
7. 后端校验：Hibernate-Validator
8. 数据库连接池：Durid

开发工具与环境

1. 开发环境：JDK1.8
2. 数据库：Mysql5.7
3. 依赖管理：Maven3
4. 缓存：Redis
5. 消息队列：RabbitMQ
6. 注册中心与配置中心：Nacos
7. 后端开发IDE：IntelliJ IDEA
8. 实体类助手插件：Lombok
9. 前端开发IDE：JetBrains WebStorm

### (三) 项目结构

```
tutor_parent
├── tutor_common -- 各微服务通用依赖
├── tutor_api -- feign远程调用API
|    ├── tutor_api_auth -- 认证授权API
|    ├── tutor_api_business -- 家教业务服务API
|    ├── tutor_api_pay -- 支付服务API
|    ├── tutor_api_system -- 系统服务API
|    └── tutor_api_upms -- 用户权限管理服务API
├── tutor_business -- 家教业务微服务
|    ├── common -- 各模块通用依赖
|	 |    ├── config -- 配置
|	 |    ├── enums -- 枚举
|	 |    ├── constant -- 常量
|	 |    ├── annotation -- 注解
|	 |    ├── util -- 工具类
|	 |    ├── exception -- 异常
|	 |    ├── handdler -- 处理器
|	 |    ├── validate -- 校验器
|	 |    ├── entity -- 实体类，如在mapper和service之间传递的PO
|	 |    └── mapper -- Mybatis数据访问层
|    ├── auth -- 前台需要认证后才能访问的接口模块
|	 |    ├── contrller -- 控制层实现
|	 |    ├── service -- 服务层实现
|	 |    └── entity -- 实体类
|	 |    		├── query -- controller入参查询提交实体
|	 |    		├── bo -- controller入参业务提交实体
|	 |    		├── vo -- controller出参实体
|	 |    		└── dto -- service与contrller之间传递的实体
|    ├── front -- 前台无需认证就能访问的接口模块
|    ├── back -- 后台管理接口模块
|    └── feign -- feign远程调用接口模块
├── tutor_auth -- 认证授权微服务
├── tutor_pay -- 支付微服务
├── tutor_system -- 系统微服务
├── tutor_upms -- 用户权限管理微服务
└── tutor_gataway -- 网关微服务
```

### (四) 功能需求

![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/01.png)

### (五) 系统架构

![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/03.png)

### (六) 代码规范

尽量遵守阿里巴巴Java开发手册，哈哈哈, 当初自己也定了一大堆规范, 实现的时候全乱套了。

### (七) 其他

1. 主要业务状态转换图（家教需求与订单）

   ![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/04.jpg)

2. Oauth2流程(以QQ登录为例)

   ![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/06.jpg)

3. 单点登录流程(整理中)

4. 微服务架构安全三种方案

   **注：该图片来源于极客时间杨波老师的课程“微服务架构160讲”中的课件**

   ![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/07.png)

5. 后台动态权限控制(简单的RBAC实现)

   ![](https://raw.githubusercontent.com/JuchiaLu/tutor-server/master/pictures/05.png)

### (八) 感谢

感谢慕课网JoJo老师的课程***Spring Security 开发安全的 REST 服务***

感谢极客时间杨波老师的课程***微服务架构 160 讲***

感谢panjiachen的开源项目***vue-element-template***

### (九) 注

由于时间紧迫，任务多，赶着答辩，很多功能都是暴力实现或未完整实现，完全违背了当初的设想和代码规范，代码中写了很多 TODO 打算重构(众所周知 TODO ≈ NEVER DO)，这只是个半成品，可用于课设或毕设，请勿用于生产环境。



## 启动说明

### (一) 项目下载

1. 下载[本项目](https://github.com/juchialu/tutor-server/)到本地
2. 下载[前台门户WEB项目](https://github.com/juchialu/tutor-portal-web/)到本地
3. 下载[后台管理WEB项目](https://github.com/juchialu/tutor-admin-web/)到本地

### (二) 本地Hosts文件配置

```
# 1. 注册中心 端口8848
127.0.0.1 tutor-register 

# 2. 网关 端口9920
127.0.0.1 tutor-gateway 

# 3. 配置中心，已和注册中心合并 端口8848
127.0.0.1 tutor-config

# 4. 认证授权微服务 端口8080
127.0.0.1 tutor-auth 

# 5. 用户微服务 端口81
127.0.0.1 tutor-upms  

# 6. 系统微服务 端口85
127.0.0.1 turor-system

# 7. 支付微服务 端口86
127.0.0.1 turor-pay

# 8. 业务微服务 端口84
127.0.0.1 turor-business

# 9. 数据库 端口3306
127.0.0.1 tutor-mysql

# 10. redis 端口6379
127.0.0.1 tutor-redis

# 11. 消息队列 端口5672
127.0.0.1 tutor-amqp

# 12. 后台管理WEB应用 端口9528
127.0.0.1 tutor-admin-web

# 13. 前台门户WEB应用 端口1234
127.0.0.1 tutor-portal-web

# 14. 单点登录WEB应用 端口8080
127.0.0.1  tutor-ssoLogin-web

# 15. 注册中心WEB应用 端口8848
127.0.0.1 tutor-register-web

# 16. 消息队列WEB应用 端口15672
127.0.0.1 tutor-amqp-web
```

### (三) 依赖服务启动

1. 启动数据库mysql(版本5.7)
2. 启动注册中心nacos(版本1.1.4)
3. 启动缓存redis(版本3.2.100)
4. 启动消息队列RabbitMQ(版本3.7.24)

注：所有依赖服务账号密码以及端口保持默认即可

### (四) 数据库新建与导入

1. 新建数据库 tutor_auth 导入 tutor_auth.sql 文件
2. 新建数据库 tutor_system 导入 tutor_system.sql 文件
3. 新建数据库 tutor_upms 导入 tutor_upms.sql 文件
4. 新建数据库 tutor_pay 导入 tutor_pay.sql 文件
5. 新建数据库 tutor_business 导入 tutor_business.sql 文件

### (五) 第三方服务配置

1. QQ等第三方登录

   auth 下的 application.yml

   ```yml
   juchia:
     security:
       social:
         qq:
           app-id: your id
           app-secret: your secret
           providerId: qq
   ```

2. 支付宝

   pay 下的 application.yml

   ```yml
   juchia:
     pay:
       aliPay:
         appId: your id
         merchantPrivateKey: your private key
         alipayPublicKey: your public key
         notifyUrl: your notify url
         returnUrl: your return url
         signType: RSA2
         charset: utf-8
         gatewayUrl: https://openapi.alipaydev.com/gateway.do
   ```

3. 七牛云存储

   system 下的 application.yml

   ```yml
   juchia:
     qiniu:
       accessKey: your access key
       secretKey: your secret key
       bucket: your bucket
       domain: your domain
   ```

4. 短信配置

   由于短信服务一般要收费，比如阿里短信最低消费225，所以测试没必要买，写了个简陋的sockes用自己的安卓手机作为短信发送服务器。

   实现**com.juchia.tutor.auth.base.authentication.code.core.Provider**接口并注入容器即可覆盖默认实现

   默认实现路径： com.juchia.tutor.auth.base.authentication.code.impl.sms.SmsCodeProvider

   [安卓短信服务器下载](#)：

   ```java
   private static String serverIP="192.168.43.1";  //手机ip地址，确保在同一网段
   private static int port=8888;  //端口号
   ```

5. 邮箱配置

   auth 下的 application.yml

   ```yml
   spring:
     mail:
       host: smtp.qq.com
       port: 587
       username: your username
       password: yuor password
   ```

### (六) 微服务启动

1. 启动网关 gateway 微服务
2. 启动系统 system 微服务
3. 启动用户中心 upms 微服务
4. 启动认证授权 auth 微服务
5. 启动支付 pay 微服务
6. 启动业务 business 微服务

### (七) WEB项目启动

1. 启动前台门户WEB项目
2. 启动后台管理WEB项目

### (八) WEB访问端点

1. 前台门户WEB应用 端口 1234
   http://tutor-portal-web:1234

2. 后台管理WEB应用 端口 9528
   http://tutor-admin-web:9528

3. 单点登录WEB应用 端口 8080
   http://tutor-ssoLogin-web:8080

4. 注册和配置中心WEB应用 端口 8848
   http://tutor-register-web:8848/nacos/index.html

   账户：nacos

   密码：nacos

5. 消息队列WEB应用 端口15672
   http://tutor-amqp-web:15672

   账户：admin

   密码：admin

6. API文档地址

   本地测试可直接下载 swagger-ui 后浏览器打开，填入以下地址

   各个微服务api文档地址

   - 业务： http://tutor-gateway:9920/business/v2/api-docs
   - 支付：http://tutor-gateway:9920/pay/v2/api-docs
   - 系统：http://tutor-gateway:9920/system/v2/api-docs
   - 用户中心：http://tutor-gateway:9920/upms/v2/api-docs
   - 认证授权中心：http://tutor-gateway:9920/auth/v2/api-docs

### (九) 图片预览

[点击查看图片预览](https://github.com/JuchiaLu/tutor-server/blob/master/PREVIEW.md)

### (十) 项目演示

1. [点击访问前台门户前端](http://47.115.38.150:4591)

   学员账户：账号：student 密码：123456

   教员账户：账号：teacher 密码：123456

   [支付宝沙箱](https://sandbox.alipaydev.com/user/downloadApp.htm) ：账号：aubhsx3836@sandbox.com 密码：111111 支付密码：111111

2. [点击访问后台管理前端](http://47.115.38.150:4592)

   超级管理员账户：账号：admin 密码：123456

   谨慎删除路由与权限(菜单)配置，测试数据每天凌晨三点重置

3. [点击访问单点登录中心](http://47.115.38.150:8080)

4. [点击访问API文档中心](http://47.115.38.150:4593)，分别输入如下地址查看各微服务API文档：
   - 业务： http://47.115.38.150/business/v2/api-docs
   - 支付：http://47.115.38.150/pay/v2/api-docs
   - 系统：http://47.115.38.150/system/v2/api-docs
   - 用户中心：http://47.115.38.150/upms/v2/api-docs
   - 认证授权中心：http://47.115.38.150/auth/v2/api-docs

注：服务器1Mbps的小水管，首次加载好几兆的程序可能要好多秒，有时候画面空白几秒加载资源是正常的。 
