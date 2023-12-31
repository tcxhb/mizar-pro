/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 47.99.170.184:3306
 Source Schema         : mizar

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 31/12/2023 09:36:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mz_alarm_rule
-- ----------------------------
DROP TABLE IF EXISTS `mz_alarm_rule`;
CREATE TABLE `mz_alarm_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `time_interval` int(11) NULL DEFAULT NULL COMMENT '时间间隔毫秒',
  `appear_limit` int(11) NULL DEFAULT NULL COMMENT '出现次数',
  `threshold` double NULL DEFAULT NULL COMMENT '阈值',
  `unit` tinyint(255) NULL DEFAULT NULL COMMENT '单位：1秒，2分',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态是否启用',
  `alarm_msg` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_alarm_rule
-- ----------------------------
INSERT INTO `mz_alarm_rule` VALUES (3, 'cpu.usage', 'CPU使用率', 3, 2, 65, 2, 0, '【告警】您的应用${app},机器${machine},CPU过高,已经使用${value}%', '2023-12-30 09:24:46', '2023-12-30 14:40:18');
INSERT INTO `mz_alarm_rule` VALUES (4, 'disk.usage', '磁盘使用率', 3, 2, 80, 2, 0, '【告警】您的应用${app},机器${machine},磁盘将满,已经使用${value}%', '2023-12-30 09:45:40', '2023-12-30 14:37:00');
INSERT INTO `mz_alarm_rule` VALUES (5, 'blockQps', '限流监控', 20, 2, 1, 1, 0, '【告警】您的应用${app},接口${resource}发生限流', '2023-12-30 09:47:30', '2023-12-30 14:40:22');

-- ----------------------------
-- Table structure for mz_app
-- ----------------------------
DROP TABLE IF EXISTS `mz_app`;
CREATE TABLE `mz_app`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `appsecret` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `version` bigint(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unq_idx`(`app_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_app
-- ----------------------------
INSERT INTO `mz_app` VALUES (42, 'mizar-dashbord', '01cf470d440e4989b47324f21f7cbca1', '控制台', '2023-12-11 08:27:54', '2023-12-25 09:26:49', 1703467609046);

-- ----------------------------
-- Table structure for mz_flow_rule
-- ----------------------------
DROP TABLE IF EXISTS `mz_flow_rule`;
CREATE TABLE `mz_flow_rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `resource_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `flow_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '流控类型',
  `flow_action` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作',
  `flow_value` bigint(255) NULL DEFAULT NULL COMMENT '阈值',
  `feature` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_flow_rule
-- ----------------------------
INSERT INTO `mz_flow_rule` VALUES (10, 'mizar-dashbord', '/api/agent/test', 'qps', 'block', 1, NULL, '2023-12-16 12:44:39', '2023-12-24 16:29:12');

-- ----------------------------
-- Table structure for mz_machine
-- ----------------------------
DROP TABLE IF EXISTS `mz_machine`;
CREATE TABLE `mz_machine`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态',
  `beat_time` datetime(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_idx`(`ip`, `app_name`, `port`) USING BTREE,
  INDEX `idx_query`(`app_name`, `status`, `update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_machine
-- ----------------------------

-- ----------------------------
-- Table structure for mz_metrics
-- ----------------------------
DROP TABLE IF EXISTS `mz_metrics`;
CREATE TABLE `mz_metrics`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `resource` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源名',
  `timestamp` datetime(3) NULL DEFAULT NULL COMMENT '监控信息的时间戳',
  `pass_qps` bigint(20) NULL DEFAULT NULL COMMENT '通过QPS',
  `success_qps` bigint(20) NULL DEFAULT NULL COMMENT '成功QPS',
  `block_qps` bigint(20) NULL DEFAULT NULL COMMENT '拒绝QPS',
  `exception_qps` bigint(20) NULL DEFAULT NULL COMMENT '异常QPS',
  `rt` double NULL DEFAULT NULL COMMENT '所有successQps的rt的和',
  `count` int(11) NULL DEFAULT NULL COMMENT '本次聚合的总条数',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_query`(`app`, `resource`, `timestamp`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2086694 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_metrics
-- ----------------------------

-- ----------------------------
-- Table structure for mz_token
-- ----------------------------
DROP TABLE IF EXISTS `mz_token`;
CREATE TABLE `mz_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `userid` bigint(20) NULL DEFAULT NULL,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `expire_time` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 517 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_token
-- ----------------------------
INSERT INTO `mz_token` VALUES (516, '87e96d452bf6434c88c0175eed4d793e', 336, 'admin', '1', 1704029692569, '2023-12-31 09:34:53', '2023-12-31 09:34:53');

-- ----------------------------
-- Table structure for mz_user
-- ----------------------------
DROP TABLE IF EXISTS `mz_user`;
CREATE TABLE `mz_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录用户名，唯一',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码，唯一',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `type` tinyint(4) NOT NULL DEFAULT 2 COMMENT '1: 超级管理源，2：其它管理员',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_username`(`username`) USING BTREE,
  UNIQUE INDEX `unique_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 388 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mz_user
-- ----------------------------
INSERT INTO `mz_user` VALUES (336, 'admin', '7917f2596f8bb70c954893f200ba6274', 'admin111', NULL, NULL, NULL, 1, '2023-09-21 09:08:13', '2023-12-30 22:39:35');
INSERT INTO `mz_user` VALUES (387, 'xxxxxxxxx', '1a52292f2cc20c721e4149f7e0b1adb6', '11', 'mizar-dashbord', NULL, NULL, 2, '2023-12-31 09:35:29', '2023-12-31 09:35:29');

SET FOREIGN_KEY_CHECKS = 1;
