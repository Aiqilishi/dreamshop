# Dream Shop 电商后端系统 API 文档

## 📋 概述

Dream Shop 是一个基于 Spring Boot 开发的电商后端系统，提供完整的电商业务 RESTful API 接口。

**基础信息：**
- 基础URL：`http://localhost:8080`
- 数据格式：JSON
- 字符编码：UTF-8
- 认证方式：JWT Token

## 🔐 认证说明

大部分接口需要在请求头中携带 JWT Token：
```
Authorization: Bearer {token}
```

## 📊 统一响应格式

```json
{
  "code": "200",
  "info": "成功",
  "data": {}
}
```

## 🛍️ 用户管理 API

### 1. 用户注册
- **接口地址：** `POST /user/insert`
- **接口描述：** 用户注册
- **请求参数：**
```json
{
  "userName": "string",
  "password": "string",
  "email": "string"
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "finish"
}
```

### 2. 用户登录
- **接口地址：** `POST /user/check`
- **接口描述：** 用户登录验证
- **请求参数：**
  - `userName` (string): 用户名
  - `passWord` (string): 密码
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 1,
    "userName": "testuser"
  }
}
```

### 3. 后台登录
- **接口地址：** `POST /user/backgroundLogin`
- **接口描述：** 后台管理员登录
- **请求参数：**
  - `userName` (string): 用户名
  - `passWord` (string): 密码
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 1,
    "roles": ["ADMIN"]
  }
}
```

### 4. 查询用户菜单
- **接口地址：** `GET /user/queryUserMenu`
- **接口描述：** 根据用户权限查询菜单
- **请求头：** 需要 JWT Token
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": [
    {
      "menuId": 1,
      "menuName": "商品管理",
      "menuUrl": "/product"
    }
  ]
}
```

## 👤 客户信息管理 API

### 1. 查询客户信息
- **接口地址：** `GET /customer/message`
- **接口描述：** 查询当前登录客户的详细信息
- **请求头：** 需要 JWT Token
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "customerId": 1,
    "nickname": "张三",
    "gender": 1,
    "birthday": "1990-01-01",
    "address": "北京市朝阳区",
    "avatarUrl": "http://example.com/avatar.jpg",
    "isSeller": 0
  }
}
```

### 2. 更新客户信息
- **接口地址：** `POST /customer/update`
- **接口描述：** 更新客户基本信息
- **请求头：** 需要 JWT Token
- **请求参数：**
```json
{
  "nickname": "string",
  "gender": 1,
  "birthday": "1990-01-01",
  "address": "string"
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "finish"
}
```

### 3. 更新客户头像
- **接口地址：** `POST /customer/updateAvatar`
- **接口描述：** 上传并更新客户头像
- **请求头：** 需要 JWT Token
- **请求参数：** 
  - `file` (multipart/form-data): 头像文件
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "http://example.com/avatar/new_avatar.jpg"
}
```

## 🛒 购物车管理 API

### 1. 添加商品到购物车
- **接口地址：** `POST /cart/insert`
- **接口描述：** 将商品添加到购物车
- **请求头：** 需要 JWT Token
- **请求参数：**
```json
{
  "productId": 1,
  "quantity": 2,
  "sellerId": 1
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "finish"
}
```

### 2. 查询购物车列表
- **接口地址：** `GET /cart/query`
- **接口描述：** 分页查询用户购物车商品列表
- **请求头：** 需要 JWT Token
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认10
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "cartId": 1,
        "productId": 1,
        "productName": "商品名称",
        "productPrice": 99.99,
        "quantity": 2,
        "totalPrice": 199.98
      }
    ]
  }
}
```

### 3. 更新购物车商品
- **接口地址：** `POST /cart/updateCart`
- **接口描述：** 更新购物车中商品数量
- **请求头：** 需要 JWT Token
- **请求参数：**
```json
{
  "productId": 1,
  "quantity": 3
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "更新成功"
}
```

### 4. 删除购物车商品
- **接口地址：** `POST /cart/deleteCart`
- **接口描述：** 删除购物车中指定商品
- **请求头：** 需要 JWT Token
- **请求参数：**
  - `productId` (long): 商品ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "删除成功"
}
```

### 5. 清空购物车
- **接口地址：** `POST /cart/deleteAllCart`
- **接口描述：** 清空用户购物车
- **请求头：** 需要 JWT Token
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "删除成功"
}
```

## 📦 商品管理 API

### 1. 分页查询商品列表
- **接口地址：** `GET /product/query`
- **接口描述：** 首页分页查询商品列表
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认5
  - `sellerId` (long, 可选): 商家ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 5,
    "total": 20,
    "list": [
      {
        "productId": 1,
        "productName": "商品名称",
        "productPrice": 99.99,
        "productImage": "http://example.com/image.jpg",
        "sellerId": 1,
        "sellerName": "商家名称"
      }
    ]
  }
}
```

### 2. 根据商品ID查询详情
- **接口地址：** `GET /product/query/{productId}`
- **接口描述：** 查询商品详细信息
- **路径参数：**
  - `productId` (long): 商品ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "productId": 1,
    "productName": "商品名称",
    "productPrice": 99.99,
    "productDescription": "商品描述",
    "productStock": 100,
    "productImages": [
      "http://example.com/image1.jpg",
      "http://example.com/image2.jpg"
    ],
    "sellerId": 1,
    "sellerName": "商家名称"
  }
}
```

### 3. 关键字搜索商品
- **接口地址：** `GET /product/queryBySearch`
- **接口描述：** 根据关键字搜索商品
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认5
  - `keyword` (string): 搜索关键字
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 5,
    "total": 10,
    "list": [
      {
        "productId": 1,
        "productName": "匹配关键字的商品",
        "productPrice": 99.99
      }
    ]
  }
}
```

### 4. 商家查询商品列表
- **接口地址：** `GET /product/queryBySeller`
- **接口描述：** 商家查询自己的商品列表
- **请求头：** 需要 JWT Token
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认5
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 5,
    "total": 15,
    "list": [
      {
        "productId": 1,
        "productName": "我的商品",
        "productPrice": 99.99,
        "productStock": 100,
        "status": 1
      }
    ]
  }
}
```

### 5. 添加商品
- **接口地址：** `POST /product/insert`
- **接口描述：** 商家添加新商品
- **请求头：** 需要 JWT Token
- **请求参数：**
```json
{
  "productName": "商品名称",
  "productPrice": 99.99,
  "productDescription": "商品描述",
  "productStock": 100,
  "categoryId": 1,
  "productImages": [
    "http://example.com/image1.jpg"
  ]
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功"
}
```

### 6. 更新商品浏览量
- **接口地址：** `POST /product/updateView/{productId}`
- **接口描述：** 增加商品浏览次数
- **路径参数：**
  - `productId` (long): 商品ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功"
}
```

### 7. 查询商品图片
- **接口地址：** `GET /product/queryImages/{productId}`
- **接口描述：** 查询商品所有图片
- **路径参数：**
  - `productId` (long): 商品ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": [
    {
      "imageId": 1,
      "imageUrl": "http://example.com/image1.jpg",
      "imageOrder": 1
    }
  ]
}
```

## 📋 订单管理 API

### 1. 创建订单
- **接口地址：** `POST /order/create`
- **接口描述：** 创建新订单并返回支付表单
- **请求头：** 需要 JWT Token
- **请求参数：**
```json
{
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2,
      "price": 99.99
    }
  ],
  "totalPrice": 199.98,
  "shippingAddress": "收货地址"
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "<form name='punchout_form' method='post' action='https://openapi.alipay.com/gateway.do'>...</form>"
}
```

### 2. 查询订单数量统计
- **接口地址：** `GET /order/queryOrderNumber`
- **接口描述：** 查询用户各状态订单数量
- **请求头：** 需要 JWT Token
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "unpaidOrderNumber": 2,
    "waitingForDeliveryOrderNumber": 1,
    "waitingForReceiptOrderNumber": 3,
    "completedOrderNumber": 10,
    "canceledOrderNumber": 1
  }
}
```

### 3. 支付结果回调
- **接口地址：** `GET /order/payment/result`
- **接口描述：** 支付宝支付成功后的回调地址
- **请求参数：**
  - `out_trade_no` (string): 订单号
- **响应：** 重定向到前端页面

### 4. 支付异步通知
- **接口地址：** `POST /order/alipay_notify_url`
- **接口描述：** 支付宝异步通知接口
- **请求参数：** 支付宝回调参数
- **响应：** "success" 或 "false"

## 🏪 商家管理 API

### 1. 商家注册
- **接口地址：** `POST /seller/insert`
- **接口描述：** 申请成为商家
- **请求头：** 需要 JWT Token
- **请求参数：**
```json
{
  "sellerName": "商家名称",
  "contactPhone": "13800138000",
  "contactPerson": "联系人",
  "address": "商家地址",
  "logoUrl": "http://example.com/logo.jpg"
}
```
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功"
}
```

### 2. 上传商家Logo
- **接口地址：** `POST /seller/uploadLogo`
- **接口描述：** 上传商家Logo图片
- **请求头：** 需要 JWT Token
- **请求参数：**
  - `file` (multipart/form-data): Logo文件
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": "http://example.com/seller/logo.jpg"
}
```

### 3. 查询商家信息
- **接口地址：** `GET /seller/queryMessage`
- **接口描述：** 查询商家详细信息
- **请求参数：**
  - `sellerId` (long): 商家ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "sellerId": 1,
    "sellerName": "商家名称",
    "contactPhone": "13800138000",
    "contactPerson": "联系人",
    "address": "商家地址",
    "logoUrl": "http://example.com/logo.jpg",
    "status": 1
  }
}
```

## 📂 分类管理 API

### 1. 添加分类
- **接口地址：** `POST /category/insert`
- **接口描述：** 添加商品分类
- **请求头：** 需要 JWT Token
- **请求参数：**
  - `categoryName` (string): 分类名称
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功"
}
```

### 2. 查询公共分类
- **接口地址：** `GET /category/query/publicCategory`
- **接口描述：** 查询所有公共分类
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": [
    {
      "categoryId": 1,
      "categoryName": "电子产品",
      "parentId": 0
    }
  ]
}
```

### 3. 查询商家分类
- **接口地址：** `GET /category/query/sellerCategory`
- **接口描述：** 查询商家自定义分类
- **请求头：** 需要 JWT Token
- **请求参数：**
  - `sellerId` (long, 可选): 商家ID
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": [
    {
      "categoryId": 2,
      "categoryName": "我的分类",
      "sellerId": 1
    }
  ]
}
```

### 4. 查询公共+商家分类
- **接口地址：** `GET /category/query/pusellerCategory`
- **接口描述：** 查询公共分类和商家分类的合集
- **请求头：** 需要 JWT Token
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": [
    {
      "categoryId": 1,
      "categoryName": "电子产品",
      "type": "public"
    },
    {
      "categoryId": 2,
      "categoryName": "我的分类",
      "type": "seller"
    }
  ]
}
```

## 👨‍💼 管理员 API

### 1. 查询所有商品
- **接口地址：** `GET /admin/queryAllProduct`
- **接口描述：** 管理员查询所有商品
- **请求头：** 需要管理员权限
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认5
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 5,
    "total": 100,
    "list": [
      {
        "productId": 1,
        "productName": "商品名称",
        "sellerName": "商家名称",
        "status": 1
      }
    ]
  }
}
```

### 2. 查询所有订单
- **接口地址：** `GET /admin/queryAllOrder`
- **接口描述：** 管理员查询所有订单
- **请求头：** 需要管理员权限
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认5
  - `status` (int, 可选): 订单状态
  - `itemStatus` (int, 可选): 订单项状态
  - `keyword` (string, 可选): 搜索关键字
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 5,
    "total": 200,
    "list": [
      {
        "orderId": "202301010001",
        "customerName": "客户姓名",
        "totalPrice": 199.98,
        "orderStatus": 1,
        "createTime": "2023-01-01 10:00:00"
      }
    ]
  }
}
```

### 3. 查询客户信息
- **接口地址：** `GET /admin/queryCustomerByFilter`
- **接口描述：** 管理员查询客户信息
- **请求头：** 需要管理员权限
- **请求参数：**
  - `pageNum` (int): 页码，默认1
  - `pageSize` (int): 每页大小，默认5
  - `status` (int, 可选): 客户状态
  - `keyword` (string, 可选): 搜索关键字
- **响应示例：**
```json
{
  "code": "200",
  "info": "成功",
  "data": {
    "pageNum": 1,
    "pageSize": 5,
    "total": 500,
    "list": [
      {
        "customerId": 1,
        "nickname": "客户昵称",
        "email": "customer@example.com",
        "registerTime": "2023-01-01 10:00:00",
        "status": 1
      }
    ]
  }
}
```

## 📝 状态码说明

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权，需要登录 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 🔄 订单状态说明

| 状态值 | 状态描述 |
|--------|----------|
| 0 | 待付款 |
| 1 | 已付款 |
| 2 | 待发货 |
| 3 | 待收货 |
| 4 | 已完成 |
| 5 | 已取消 |