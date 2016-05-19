/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-05-19 10:18:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business_log
-- ----------------------------
DROP TABLE IF EXISTS `business_log`;
CREATE TABLE `business_log` (
  `BusinessFlowNum` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增序列号',
  `userid` varchar(50) DEFAULT NULL,
  `OperatorTime` datetime DEFAULT NULL,
  `RemoteIP` varchar(15) DEFAULT NULL COMMENT '登录用户的客户端地址',
  `URL` varchar(256) DEFAULT NULL COMMENT '访问咱们系统的URL',
  `AccessParams` varchar(2000) DEFAULT NULL COMMENT '访问action操作时附带的参数',
  `userLog` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`BusinessFlowNum`)
) ENGINE=InnoDB AUTO_INCREMENT=23425 DEFAULT CHARSET=utf8 COMMENT='业务日志';
