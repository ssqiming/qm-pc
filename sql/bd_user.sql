/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-05-23 14:13:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bd_user
-- ----------------------------
DROP TABLE IF EXISTS `bd_user`;
CREATE TABLE `bd_user` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号码',
  `sex` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `account` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '支付宝账户',
  `role` int(2) DEFAULT '2' COMMENT '角色（1：管理员用户，2：普通用户）',
  `state` varchar(10) CHARACTER SET utf8 DEFAULT '1' COMMENT '状态',
  `check_code` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '验证码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` varchar(10) CHARACTER SET utf8 DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
