# Dream Shop 电商后端系统

## 项目概述

Dream Shop 是一个基于 **Spring Boot** 开发的电商单体结构后端系统。项目实现了完整的电商业务流程，包括用户管理、商品管理、订单处理、支付集成等核心功能，通过整合 Redis 缓存、RocketMQ 消息队列等中间件，构建了高性能、可扩展的现代化电商后端服务。

## 技术栈

### 核心框架
- **Spring Boot 2.7+** - 主框架
- **Spring Web** - Web层框架
- **Maven** - 项目构建工具

### 数据层
- **MyBatis** - 持久层框架
- **MySQL 8.0** - 关系型数据库
- **Redis** - 缓存中间件
- **HikariCP** - 数据库连接池

### 消息队列
- **RocketMQ** - 分布式消息队列
- **延时消息** - 订单超时处理
- **异步消息** - 业务解耦

### 第三方集成
- **支付宝支付** - 第三方支付接口
- **JWT** - 身份认证Token

### 开发工具
- **Lombok** - 代码简化
- **Jackson** - JSON处理
- **Validation** - 参数校验
- **Logback** - 日志框架

## 核心功能

### 用户管理
- 用户注册登录（JWT Token认证）
- 用户信息管理
- 商家申请与审核
- 权限控制与安全防护

### 商品管理
- 商品CRUD操作
- 商品分类管理
- 商品库存管理
- 商品搜索接口

### 订单系统
- 订单创建与管理
- 订单状态流转
- 订单超时处理（基于RocketMQ延时消息）
- 订单查询与统计

### 支付系统
- 支付宝支付接口集成
- 支付回调处理
- 支付状态同步
- 异步支付通知

### 库存管理
- 库存扣减与释放
- 基于消息队列的异步库存处理
- 库存锁定机制
- 库存预警功能

## 系统架构

### 整体架构
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controller    │────│    Service      │────│       DAO       │
│   控制层        │    │    业务层       │    │     数据层      │
│ • RESTful API   │    │ • 业务逻辑      │    │ • MySQL         │
│ • 参数校验      │    │ • 事务管理      │    │ • Redis         │
│ • 异常处理      │    │ • 缓存处理      │    │ • MyBatis       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                ↓
                       ┌─────────────────┐
                       │   RocketMQ      │
                       │   消息队列      │
                       │ • 异步处理      │
                       │ • 延时消息      │
                       │ • 业务解耦      │
                       └─────────────────┘
```

### 消息队列架构
```
订单创建 ──→ RocketMQ ──→ 库存扣减
    │                        │
    ↓                        ↓
支付超时处理 ←──── 延时消息 ←──── 库存释放
```

## 🔧 核心特性

### 1. 异步消息处理
- 使用 RocketMQ 实现订单、库存、支付的异步解耦
- 延时消息处理订单超时场景
- 消息可靠性保证和异常处理
- 消息重试机制和死信队列

### 2. 缓存优化
- Redis 缓存用户信息和商品数据
- 缓存更新策略和一致性保证
- 分布式锁防止缓存击穿
- 缓存预热和过期策略

### 3. 支付集成
- 支付宝支付接口集成
- 支付回调和异步通知处理
- 支付状态同步和异常处理
- 支付安全验证

### 4. 安全设计
- JWT Token 身份认证
- 拦截器权限控制
- 密码加密存储
- 接口安全防护和参数校验

### 5. 数据库优化
- MyBatis 数据访问层
- 数据库连接池优化
- 索引设计和查询优化
- 分页查询和批量操作

## 项目结构

```
dream-shop/
├── src/main/java/cn/mmko/
│   ├── Application.java    # 启动类
│   ├── controller/         # 控制层 - RESTful API接口
│   │   ├── AdminController.java      # 管理员控制器
│   │   ├── CartController.java       # 购物车控制器
│   │   ├── CategoryController.java   # 分类控制器
│   │   ├── CustomerController.java   # 用户控制器
│   │   ├── OrderController.java      # 订单控制器
│   │   ├── PayController.java        # 支付控制器
│   │   ├── ProductController.java    # 商品控制器
│   │   ├── SellerController.java     # 商家控制器
│   │   ├── UserController.java       # 用户管理控制器
│   │   ├── handler/        # 处理器
│   │   │   ├── OrderTimeoutHandler.java # 订单超时处理
│   │   │   └── StockDomainService.java  # 库存领域服务
│   │   ├── job/            # 定时任务
│   │   │   └── TimeoutCloseOrderJob.java # 超时关闭订单任务
│   │   └── mq/             # 消息队列
│   │       ├── consumer/   # 消息消费者
│   │       └── producer/   # 消息生产者
│   ├── service/            # 业务层 - 业务逻辑处理
│   │   ├── admin/          # 管理员服务
│   │   │   ├── IAdminService.java
│   │   │   └── imp/        # 实现类
│   │   ├── cart/           # 购物车服务
│   │   │   ├── ICartService.java
│   │   │   └── imp/
│   │   ├── category/       # 分类服务
│   │   │   ├── ICategoryService.java
│   │   │   └── imp/
│   │   ├── customer/       # 用户服务
│   │   │   ├── ICustomerService.java
│   │   │   └── imp/
│   │   ├── order/          # 订单服务
│   │   │   ├── IOrderService.java
│   │   │   └── imp/
│   │   ├── product/        # 商品服务
│   │   │   ├── IProductService.java
│   │   │   ├── IProductQueryService.java
│   │   │   └── imp/
│   │   ├── seller/         # 商家服务
│   │   │   ├── ISellerService.java
│   │   │   └── imp/
│   │   └── utils/          # 服务工具类
│   │       ├── IPasswordEncryptionService.java
│   │       └── imp/
│   ├── dao/                # 数据访问层 - MyBatis接口
│   │   ├── IAdminDao.java
│   │   ├── ICartDao.java
│   │   ├── ICategoryDao.java
│   │   ├── ICustomerDao.java
│   │   ├── IOrderDao.java
│   │   ├── IOrderItemDao.java
│   │   ├── IProductDao.java
│   │   ├── ISellerDao.java
│   │   └── IUserDao.java
│   ├── po/                 # 持久化对象 - 数据库表映射
│   │   ├── AdminPo.java
│   │   ├── CartPo.java
│   │   ├── CategoryPo.java
│   │   ├── CustomerPo.java
│   │   ├── OrderPo.java
│   │   ├── OrderItemPo.java
│   │   ├── ProductPo.java
│   │   ├── SellerPo.java
│   │   └── UserPo.java
│   ├── dto/                # 数据传输对象
│   │   ├── CartCreateDTO.java
│   │   ├── CategoryDTO.java
│   │   ├── LoginRequestDTO.java
│   │   ├── LoginResponseDTO.java
│   │   ├── OrderCreateDTO.java
│   │   ├── SellerCreateDTO.java
│   │   └── product/        # 商品相关DTO
│   │       ├── ProductCreateDTO.java
│   │       ├── ProductDetailDTO.java
│   │       └── ProductListDTO.java
│   ├── vo/                 # 视图对象
│   │   ├── CartListVO.java
│   │   ├── CategoryVO.java
│   │   ├── CustomerInfoVO.java
│   │   ├── OrderListVO.java
│   │   ├── ProductInfoVO.java
│   │   └── ProductListVO.java
│   ├── config/             # 配置类
│   │   ├── AliPayConfig.java         # 支付宝配置
│   │   ├── CorsConfig.java           # 跨域配置
│   │   ├── RedisClientConfig.java    # Redis配置
│   │   ├── RocketMQConfig.java       # 消息队列配置
│   │   ├── ThreadPoolConfig.java     # 线程池配置
│   │   └── WebMvcConfig.java         # Web MVC配置
│   ├── interceptor/        # 拦截器
│   │   └── JwtInterceptor.java       # JWT拦截器
│   ├── message/            # 消息实体
│   │   ├── OrderMessage.java         # 订单消息
│   │   ├── OrderTimeoutMessage.java  # 订单超时消息
│   │   └── StockMessage.java         # 库存消息
│   ├── redis/              # Redis服务
│   │   ├── IRedisService.java
│   │   └── RedissonService.java
│   ├── common/             # 公共组件
│   │   ├── Constants.java            # 常量类
│   │   └── RocketMQConstants.java    # MQ常量
│   ├── enums/              # 枚举类
│   │   └── ResponseCode.java         # 响应码枚举
│   ├── annotation/         # 自定义注解
│   ├── utils/              # 工具类
│   │   └── JwtUtils.java             # JWT工具类
│   ├── response/           # 响应封装
│   │   └── Response.java             # 统一响应格式
│   └── exception/          # 异常处理
│       ├── AppException.java         # 应用异常
│       └── GlobalExceptionHandler.java # 全局异常处理器
├── src/main/resources/
│   ├── mapper/             # MyBatis映射文件
│   ├── application.yml     # 主配置文件
│   └── logback-spring.xml  # 日志配置
├── src/test/java/          # 测试代码
├── docs/                   # 文档目录
│   └── dev-ops/           # 运维相关
├── data/log/              # 日志文件
├── pom.xml                # Maven配置
└── README.md              # 项目说明
```

## 快速开始

### 环境要求
- JDK 8+
- MySQL 8.0+
- Redis 6.0+
- RocketMQ 4.9+
- Maven 3.6+

### 启动步骤
```bash
# 1. 克隆项目
git clone https://gitcode.com/mmko/dream-shop.git
cd dream-shop

# 2. 创建数据库
mysql -u root -p
CREATE DATABASE dream_shop DEFAULT CHARACTER SET utf8mb4;

# 3. 修改配置文件
vim src/main/resources/application.yml
# 配置数据库连接、Redis连接、RocketMQ地址等

# 4. 启动Redis
redis-server

# 5. 启动RocketMQ
# 启动NameServer
sh mqnamesrv
# 启动Broker
sh mqbroker -n localhost:9876

# 6. 编译并启动项目
mvn clean compile
mvn spring-boot:run
```

## 项目亮点

1. **完整的业务流程**：涵盖电商后端的核心功能模块
2. **现代化技术栈**：使用主流的Java开发框架和工具
3. **异步消息处理**：基于RocketMQ的分布式消息解决方案
4. **支付系统集成**：真实的第三方支付接口集成
5. **缓存优化**：Redis缓存提升系统性能
6. **安全设计**：完善的认证和权限控制
7. **高并发处理**：消息队列和缓存的合理使用
8. **代码规范**：良好的分层架构和代码组织

## 性能优化

- **数据库优化**：索引设计、查询优化、连接池配置
- **缓存策略**：Redis缓存、缓存更新策略、分布式锁
- **异步处理**：消息队列异步解耦、批量处理
- **JVM优化**：内存配置、GC调优

## 技术特色

### 消息队列应用
- **订单超时处理**：延时消息自动取消超时订单
- **库存异步扣减**：消息队列解耦库存操作
- **支付结果通知**：异步处理支付回调
- **消息可靠性**：消息重试和死信队列

### 缓存设计
- **用户信息缓存**：提升用户查询性能
- **商品数据缓存**：热点商品数据缓存
- **分布式锁**：防止缓存击穿和并发问题
- **缓存一致性**：缓存更新策略

### 安全机制
- **JWT认证**：无状态的用户认证
- **拦截器控制**：基于JWT的访问控制
- **参数校验**：接口参数安全校验
- **SQL注入防护**：MyBatis预编译防护

## 开发者

**项目作者**：方其毅  
**联系方式**：2804771825@qq.com  
**项目地址**：https://gitcode.com/mmko/dream-shop.git
