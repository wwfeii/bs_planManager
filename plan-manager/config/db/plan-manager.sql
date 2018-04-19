/*
Navicat MySQL Data Transfer

Source Server         : bs
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : plan-manager

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-17 21:09:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_node`;
CREATE TABLE `tb_node` (
  `NODE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `NODE_NAME` varchar(50) NOT NULL,
  `NODE_NO` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `ROLE_NAME` varchar(50) NOT NULL,
  `NODE_TYPE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`NODE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_node
-- ----------------------------
INSERT INTO `tb_node` VALUES ('1', '2018-04-10 19:52:26', '1', 'admin', '2018-04-10 19:52:26', '1', 'admin', 'test1', '1', '4', '任务负责人', null);
INSERT INTO `tb_node` VALUES ('3', '2018-04-10 19:53:22', '1', 'admin', '2018-04-10 19:53:22', '1', 'admin', 'test3', '3', '1', '管理员', null);
INSERT INTO `tb_node` VALUES ('5', '2018-04-10 20:39:43', '1', 'admin', '2018-04-10 20:39:43', '1', 'admin', 'test2', '2', '4', '任务负责人', null);
INSERT INTO `tb_node` VALUES ('6', '2018-04-13 17:43:08', '1', 'admin', '2018-04-13 17:43:08', '1', 'admin', '新建', '1', '3', '计划负责人', 'typePlan');
INSERT INTO `tb_node` VALUES ('7', '2018-04-13 17:43:23', '1', 'admin', '2018-04-13 17:43:23', '1', 'admin', '管理员审核', '2', '1', '管理员', 'typePlan');

-- ----------------------------
-- Table structure for tb_plan
-- ----------------------------
DROP TABLE IF EXISTS `tb_plan`;
CREATE TABLE `tb_plan` (
  `PLAN_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `PLAN_LEADER_ID` bigint(20) NOT NULL,
  `PLAN_LEADER_NAME` varchar(50) NOT NULL,
  `PLAN_NAME` varchar(50) NOT NULL,
  `PLAN_STATUS` varchar(50) NOT NULL,
  `PROJECT_ID` bigint(20) NOT NULL,
  `PROJECT_NAME` varchar(50) NOT NULL,
  `TASK_DISFINISH_NUM` int(11) DEFAULT NULL,
  `TASK_FINISH_NUM` int(11) DEFAULT NULL,
  `TASK_TOTAL_NUM` int(11) DEFAULT NULL,
  `PLAN_PROCESSNO` int(11) DEFAULT NULL,
  `PROCESS_NEXT_USERID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PLAN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_plan
-- ----------------------------
INSERT INTO `tb_plan` VALUES ('5', '2018-04-13 17:58:10', '1', 'admin', '2018-04-13 17:58:10', '1', 'admin', '3', '计划哥', '44', '新建', '4', 'test1', '0', '0', '0', '1', '1');
INSERT INTO `tb_plan` VALUES ('6', '2018-04-14 16:49:30', '1', 'admin', '2018-04-14 16:49:30', '1', 'admin', '3', '计划哥', '55', '进行中', '4', 'test1', '0', '0', '0', '3', '1');
INSERT INTO `tb_plan` VALUES ('7', '2018-04-14 16:57:53', '1', 'admin', '2018-04-14 16:57:53', '1', 'admin', '3', '计划哥', '66', '进行中', '4', 'test1', '2', '0', '2', '3', '1');

-- ----------------------------
-- Table structure for tb_process_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_process_log`;
CREATE TABLE `tb_process_log` (
  `LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `BUSINESS_ID` bigint(20) NOT NULL,
  `CHECK_INFO` longtext NOT NULL,
  `HANDLEAR_ID` bigint(20) NOT NULL,
  `OPERATE_TYPE` varchar(50) NOT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_process_log
-- ----------------------------
INSERT INTO `tb_process_log` VALUES ('1', '2018-04-14 11:40:51', '1', 'admin', '2018-04-14 11:40:51', '1', 'admin', '5', 'test', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('2', '2018-04-14 16:49:43', '1', 'admin', '2018-04-14 16:49:43', '1', 'admin', '6', '66', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('3', '2018-04-14 16:57:19', '1', 'admin', '2018-04-14 16:57:19', '1', 'admin', '6', 'zz', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('4', '2018-04-14 16:58:32', '1', 'admin', '2018-04-14 16:58:32', '1', 'admin', '7', 'this is one', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('5', '2018-04-14 16:58:50', '1', 'admin', '2018-04-14 16:58:50', '1', 'admin', '7', 'this is two', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('6', '2018-04-14 18:15:03', '1', 'admin', '2018-04-14 18:15:03', '1', 'admin', '5', '55', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('7', '2018-04-14 18:21:05', '1', 'admin', '2018-04-14 18:21:05', '1', 'admin', '5', '555', '1', '审核');
INSERT INTO `tb_process_log` VALUES ('8', '2018-04-14 18:21:13', '1', 'admin', '2018-04-14 18:21:13', '1', 'admin', '5', '1', '1', '审核');

-- ----------------------------
-- Table structure for tb_project
-- ----------------------------
DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE `tb_project` (
  `PROJECT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `PROJECT_LEADER_ID` bigint(20) DEFAULT NULL,
  `PROJECT_LEADER_NAME` varchar(50) DEFAULT NULL,
  `PROJECT_NAME` varchar(50) NOT NULL,
  `PROJECT_STATUS` varchar(50) NOT NULL,
  PRIMARY KEY (`PROJECT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_project
-- ----------------------------
INSERT INTO `tb_project` VALUES ('4', '2018-04-13 17:32:44', '1', 'admin', '2018-04-13 17:32:44', '1', 'admin', '2', '项目哥', 'test1', '新建');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `ROLE_DESCRIPTION` longtext,
  `ROLE_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', '2018-03-23 13:22:30', '1', '管理员', '2018-03-23 13:22:30', '1', '管理员', '超级管理员，拥有最大的操作权限', '管理员');
INSERT INTO `tb_role` VALUES ('2', '2018-03-23 13:22:30', '1', '管理员', '2018-03-23 13:22:30', '1', '管理员', '主要是负责项目', '项目负责人');
INSERT INTO `tb_role` VALUES ('3', '2018-03-23 13:22:30', '1', '管理员', '2018-03-23 13:22:30', '1', '管理员', '主要是负责计划', '计划负责人');
INSERT INTO `tb_role` VALUES ('4', '2018-03-23 13:22:30', '1', '管理员', '2018-03-23 13:22:30', '1', '管理员', '主要负责任务的', '任务负责人');
INSERT INTO `tb_role` VALUES ('7', '2018-04-16 10:46:45', '1', 'admin', '2018-04-16 10:46:45', '1', 'admin', '用户计划流程审核节点中的首席审核', '首席审核');
INSERT INTO `tb_role` VALUES ('9', '2018-04-16 10:55:17', '1', 'admin', '2018-04-16 10:55:17', '1', 'admin', '该角色用户计划流程审核，', '经理审核');

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
  `TASK_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `PLAN_ID` bigint(20) NOT NULL,
  `PLAN_NAME` varchar(50) NOT NULL,
  `TASK_DESCRIPTION` longtext,
  `TASK_LEADER_ID` bigint(20) NOT NULL,
  `TASK_LEADER_NAME` varchar(50) NOT NULL,
  `TASK_STATUS` varchar(50) NOT NULL,
  `TASK_TITLE` varchar(50) NOT NULL,
  `CHECK_INFO` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_task
-- ----------------------------
INSERT INTO `tb_task` VALUES ('5', '2018-04-14 16:58:07', '1', 'admin', '2018-04-14 16:58:07', '4', '任务哥', '6', 'zz', '1', '4', '任务哥', '审核中', '3', '哈哈，我就是测试哦哦哦');
INSERT INTO `tb_task` VALUES ('6', '2018-04-13 17:33:28', '1', 'admin', '2018-04-13 17:33:28', '1', 'admin', '3', 'test1-上部计划', '111', '4', '任务哥', '待执行', '任务1', null);
INSERT INTO `tb_task` VALUES ('7', '2018-04-14 16:58:00', '1', 'admin', '2018-04-14 16:58:00', '1', 'admin', '7', '66', '1', '4', '任务哥', '待执行', '1', null);
INSERT INTO `tb_task` VALUES ('8', '2018-04-14 16:58:07', '1', 'admin', '2018-04-14 16:58:07', '1', 'admin', '7', '66', '2', '4', '任务哥', '待执行', '2', null);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` varchar(32) NOT NULL,
  `CREATOR_ID` bigint(20) NOT NULL,
  `CREATOR_NAME` varchar(50) NOT NULL,
  `UPDATED_TIME` varchar(32) DEFAULT NULL,
  `UPDATOR_ID` bigint(20) DEFAULT NULL,
  `UPDATOR_NAME` varchar(50) DEFAULT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `ROLE_NAME` varchar(50) NOT NULL,
  `TEL_PHONE` varchar(50) NOT NULL,
  `USER_NAME` varchar(20) NOT NULL,
  `USER_PWD` varchar(50) NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '2018-03-23 13:22:30', '1', '管理员', '2018-03-23 13:22:30', '1', '管理员', '1', '管理员', '15760077621', 'admin', '21232F297A57A5A743894A0E4A801FC3');
INSERT INTO `tb_user` VALUES ('2', '2018-04-07 09:16:59', '1', '管理员', '2018-04-07 09:16:59', '1', '管理员', '2', '项目负责人', '15878909822', '项目哥', 'F379EAF3C831B04DE153469D1BEC345E');
INSERT INTO `tb_user` VALUES ('3', '2018-04-07 09:17:14', '1', '管理员', '2018-04-07 09:17:14', '1', '管理员', '3', '计划负责人', '15789082323', '计划哥', 'F379EAF3C831B04DE153469D1BEC345E');
INSERT INTO `tb_user` VALUES ('4', '2018-04-07 18:40:10', '1', 'admin', '2018-04-07 18:40:10', '1', 'admin', '4', '任务负责人', '15789892222', '任务哥', 'F379EAF3C831B04DE153469D1BEC345E');
