/*
Navicat MySQL Data Transfer

Source Server         : 112.74.183.177
Source Server Version : 50718
Source Host           : 112.74.183.177:3306
Source Database       : tio_site

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-07-24 13:48:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(32) NOT NULL,
  `pwd` varchar(64) NOT NULL,
  `salt` varchar(16) NOT NULL,
  `nick` varchar(16) NOT NULL,
  `avatar` varchar(64) CHARACTER SET utf8 NOT NULL,
  `ip` varchar(16) CHARACTER SET utf8 NOT NULL COMMENT '注册ip',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户状态, 1：正常，2：注销，3：被拉黑',
  PRIMARY KEY (`id`),
  KEY `loginname` (`loginname`,`pwd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of blog
-- ----------------------------

-- ----------------------------
-- Table structure for donate
-- ----------------------------
DROP TABLE IF EXISTS `donate`;
CREATE TABLE `donate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `name` varchar(16) NOT NULL,
  `url` varchar(128) DEFAULT NULL COMMENT '用户或公司的url',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `way` varchar(32) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of donate
-- ----------------------------


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(32) NOT NULL,
  `pwd` varchar(64) NOT NULL,
  `salt` varchar(16) NOT NULL,
  `nick` varchar(16) NOT NULL,
  `avatar` varchar(64) CHARACTER SET utf8 NOT NULL,
  `ip` varchar(16) CHARACTER SET utf8 NOT NULL COMMENT '注册ip',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户状态, 1：正常，2：注销，3：被拉黑',
  PRIMARY KEY (`id`),
  KEY `loginname` (`loginname`,`pwd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_osc
-- ----------------------------
DROP TABLE IF EXISTS `user_osc`;
CREATE TABLE `user_osc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `oscid` varchar(32) NOT NULL,
  `oscnick` varchar(64) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_osc
-- ----------------------------
