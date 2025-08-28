/*
 Navicat Premium Data Transfer

 Source Server         : dream
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:33307
 Source Schema         : dream_db

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 03/07/2025 11:31:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `admin_id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员账号',
  `password_hash` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(1:正常,0:禁用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', 'admin123456hash', 'admin@example.com', 1, '2025-07-03 11:02:45');
INSERT INTO `t_admin` VALUES (2, 'superadmin', 'superadminhash', 'superadmin@example.com', 1, '2025-07-03 11:02:45');

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart`  (
  `cart_id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`cart_id`) USING BTREE,
  UNIQUE INDEX `uk_user_product`(`user_id` ASC, `product_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cart
-- ----------------------------

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `category_sort` int NULL DEFAULT 0 COMMENT '排序权重',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父分类ID，0为一级分类',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, '手机数码', 1, 0, '2025-07-01 14:20:51');
INSERT INTO `t_category` VALUES (2, '电脑办公', 2, 0, '2025-07-01 14:20:51');
INSERT INTO `t_category` VALUES (3, '服装鞋帽', 3, 0, '2025-07-01 14:20:51');
INSERT INTO `t_category` VALUES (4, '食品饮料', 4, 0, '2025-07-01 14:20:51');
INSERT INTO `t_category` VALUES (5, '家居用品', 5, 0, '2025-07-01 14:20:51');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '下单用户ID',
  `seller_id` bigint NOT NULL COMMENT '商家ID',
  `order_status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态(0:待支付,1:已支付,2:已发货,3:已完成,4:已取消)',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (1, 1, 3, 1, 7999.00, '2025-07-03 10:00:00', NULL, NULL, NULL);
INSERT INTO `t_order` VALUES (2, 5, 4, 2, 899.00, '2025-07-03 11:00:00', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `quantity` int NOT NULL COMMENT '购买数量',
  PRIMARY KEY (`item_id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
INSERT INTO `t_order_item` VALUES (1, 1, 1, 'iPhone 15 Pro', 7999.00, 1);
INSERT INTO `t_order_item` VALUES (2, 2, 3, 'Nike运动鞋', 899.00, 1);

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `seller_id` bigint NOT NULL DEFAULT 0 COMMENT '商家ID',
  `product_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `product_stock` int NOT NULL DEFAULT 0 COMMENT '库存数量',
  `product_sales` int NOT NULL DEFAULT 0 COMMENT '销量',
  `product_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '商品状态(1:上架,0:下架)',
  `product_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品描述',
  `product_image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图',
  `is_recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否推荐(1:是,0:否)',
  `is_hot` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否热销(1:是,0:否)',
  `view_count` bigint NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_product_status`(`product_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (1, 'iPhone 15 Pro', 1, 3, 7999.00, 100, 50, 1, '最新款iPhone，搭载A17 Pro芯片', 'https://example.com/iphone15.jpg', 1, 1, 1000, '2025-07-01 14:21:00', '2025-07-03 11:03:31');
INSERT INTO `t_product` VALUES (2, 'MacBook Pro 14', 2, 3, 14999.00, 50, 20, 1, '专业级笔记本电脑', 'https://example.com/macbook.jpg', 1, 0, 800, '2025-07-01 14:21:00', '2025-07-03 11:03:31');
INSERT INTO `t_product` VALUES (3, 'Nike运动鞋', 3, 4, 899.00, 200, 150, 1, '舒适透气的运动鞋', 'https://example.com/nike.jpg', 0, 1, 500, '2025-07-01 14:21:00', '2025-07-03 11:03:31');
INSERT INTO `t_product` VALUES (4, '可口可乐', 4, 4, 3.50, 1000, 800, 1, '经典碳酸饮料', 'https://example.com/cola.jpg', 0, 0, 200, '2025-07-01 14:21:00', '2025-07-03 11:03:31');
INSERT INTO `t_product` VALUES (5, '台灯', 5, 4, 199.00, 80, 30, 1, '护眼LED台灯', 'https://example.com/lamp.jpg', 0, 0, 300, '2025-07-01 14:21:00', '2025-07-03 11:03:31');

-- ----------------------------
-- Table structure for t_product_image
-- ----------------------------
DROP TABLE IF EXISTS `t_product_image`;
CREATE TABLE `t_product_image`  (
  `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片URL',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`image_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_image
-- ----------------------------
INSERT INTO `t_product_image` VALUES (1, 1, 'https://example.com/iphone15_1.jpg', 1, '2025-07-03 11:03:00');
INSERT INTO `t_product_image` VALUES (2, 1, 'https://example.com/iphone15_2.jpg', 2, '2025-07-03 11:03:00');
INSERT INTO `t_product_image` VALUES (3, 2, 'https://example.com/macbook_1.jpg', 1, '2025-07-03 11:03:00');
INSERT INTO `t_product_image` VALUES (4, 2, 'https://example.com/macbook_2.jpg', 2, '2025-07-03 11:03:00');
INSERT INTO `t_product_image` VALUES (5, 3, 'https://example.com/nike_1.jpg', 1, '2025-07-03 11:03:00');
INSERT INTO `t_product_image` VALUES (6, 3, 'https://example.com/nike_2.jpg', 2, '2025-07-03 11:03:00');

-- ----------------------------
-- Table structure for t_seller
-- ----------------------------
DROP TABLE IF EXISTS `t_seller`;
CREATE TABLE `t_seller`  (
  `seller_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商家ID',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `seller_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商家名称',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(1:正常,0:禁用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seller_id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商家表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seller
-- ----------------------------
INSERT INTO `t_seller` VALUES (1, 3, '数码之家', '13800000001', 1, '2025-07-03 11:02:40');
INSERT INTO `t_seller` VALUES (2, 4, '潮流服饰', '13800000002', 1, '2025-07-03 11:02:40');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password_hash` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密后的密码',
  `password_salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码盐值',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `user_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态(1:正常,0:禁用,-1:删除)',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型(0:普通用户,1:商家,2:管理员)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除(0:否,1:是)',
  `register_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `idx_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `idx_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'mmko', 'kI/4qDmsQyr9NqsA5e6UEmVI20V+WJa65TAfIJILOCc=', 'lfGeHrKEy2yywUvj5BsRFw==', NULL, NULL, NULL, 1, 0, 0, '2025-06-30 09:27:43', NULL, NULL);
INSERT INTO `t_user` VALUES (3, 'makima', 'ML4eDGFkwM8rldxxm0JMMtzaem6WmJcTl8cvqbSnlF0=', '6LH9BDOp+9MVybpUXyz4Ew==', NULL, NULL, NULL, 1, 1, 0, '2025-06-30 14:48:40', NULL, NULL);
INSERT INTO `t_user` VALUES (4, 'makim', 'Hb53t0iZ0DaeLA3wdv8DCe1oDLQ9unGfBFYIly49ebU=', '/5TXNH6s7AXh7xUk+p1uwg==', NULL, NULL, NULL, 1, 1, 0, '2025-06-30 15:47:51', NULL, NULL);
INSERT INTO `t_user` VALUES (5, 'Aiqilishi', 'gr9UIh9UKcrdcfAYZgn/+hgm4S4WstZgKk+pRS7+p/w=', 'gbw/egXeqeSoBB8dNLPHqQ==', NULL, NULL, NULL, 1, 0, 0, '2025-06-30 15:49:26', NULL, NULL);
INSERT INTO `t_user` VALUES (6, 'testuser', '0zVsJRzFPvvvBS4u3EtPZR+thNT/mhrX4TrerKwsNPc=', 'HC9bCubm6/4ukyrrYoAcqw==', NULL, NULL, NULL, 1, 0, 0, '2025-06-30 15:52:05', NULL, NULL);
INSERT INTO `t_user` VALUES (8, 'ma132', 'rreKhpbZzvl0L9pkCxa9md+4ibdyNmbw1/etZfBDOuI=', 'tiNTmfPure/9SArBBvrR+w==', NULL, NULL, NULL, 1, 0, 0, '2025-06-30 16:10:49', NULL, NULL);
INSERT INTO `t_user` VALUES (9, 'qwee', 'ksyEVq0KwyGLxpBTSSHAKr75eB6G5EPDUmDbIUilKvE=', '7rUQMAYtz0AiZ2H3yjkMWQ==', NULL, NULL, NULL, 1, 0, 0, '2025-06-30 16:14:12', NULL, NULL);
INSERT INTO `t_user` VALUES (10, 'FQY', 'auF9ds2x9o3YtydegRjEHpvXmDopnmDVruWoqM/9iBc=', 'NBnlZbFVoltetoSSMux4yQ==', NULL, NULL, NULL, 1, 2, 0, '2025-06-30 16:16:01', NULL, NULL);
INSERT INTO `t_user` VALUES (11, 'cccc', '1E665qO40Jwzow0wEATG6IAOMvDjoTm4vCFCGiapMM4=', 'hTBjlIomwRR5eULsLUfguw==', NULL, NULL, NULL, 1, 0, 0, '2025-07-01 16:30:49', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
