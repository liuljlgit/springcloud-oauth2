/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.136
Source Server Version : 50720
Source Host           : 192.168.1.136:3306
Source Database       : oauth2

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-13 10:26:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `clientdetails`
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails` (
  `appId` varchar(256) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of clientdetails
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_access_token`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_approvals`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` datetime DEFAULT NULL,
  `lastModifiedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_client_token`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_code`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_refresh_token`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_company`
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
  `sc_id` bigint(20) NOT NULL COMMENT '主键',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `address` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '公司联系电话',
  `email` varchar(64) DEFAULT NULL COMMENT 'email地址',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【系统配置】6、公司表';

-- ----------------------------
-- Records of sys_company
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `sd_id` bigint(20) NOT NULL COMMENT '主键',
  `psd_id` bigint(20) DEFAULT NULL COMMENT '父级部门',
  `sc_id` bigint(20) DEFAULT NULL COMMENT '归属公司',
  `dept_name` varchar(64) DEFAULT NULL COMMENT '部门名称',
  `address` varchar(255) DEFAULT NULL COMMENT '部门地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '部门电话号码',
  `email` varchar(64) DEFAULT NULL COMMENT 'email地址',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sd_id`),
  KEY `IDX_SCID` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【系统配置】5、部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dept_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_role`;
CREATE TABLE `sys_dept_role` (
  `sdr_id` bigint(20) NOT NULL COMMENT '主键',
  `sd_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `sr_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sdr_id`),
  KEY `IDX_SRID` (`sr_id`) USING BTREE,
  KEY `IDX_SDID` (`sd_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【配置关系】4、部门-角色表';

-- ----------------------------
-- Records of sys_dept_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `sm_id` bigint(20) NOT NULL COMMENT '主键',
  `psm_id` bigint(20) DEFAULT NULL COMMENT '父级菜单',
  `name` varchar(128) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(128) DEFAULT NULL COMMENT '链接地址',
  `sort` int(10) DEFAULT NULL COMMENT '菜单排序',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【系统配置】4、菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `sp_id` bigint(20) NOT NULL COMMENT '主键',
  `perm_name` varchar(64) DEFAULT NULL COMMENT '权限名称',
  `description` varchar(256) DEFAULT NULL COMMENT '权限描述',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【系统配置】3、权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('10002', 'ad', 'asd', '2018-10-30 17:48:22', '1', '2018-10-30 17:48:25');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `sr_id` bigint(20) NOT NULL COMMENT '主键',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `role_category` tinyint(4) DEFAULT NULL COMMENT '【角色类别】1、公用角色，2、部门专属角色，3：公司专属角色',
  `role_type` tinyint(4) DEFAULT NULL COMMENT '【角色类型】1、系统管理员，2、部门管理员，3：公司管理员，4、普通角色',
  `description` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【系统配置】2、角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1000001', '超级管理员', '1', '1', '全系统超级管理员', null, null, null);
INSERT INTO `sys_role` VALUES ('1000002', '超级管理员', '1', '1', '全系统超级管理员', null, null, null);

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `srm_id` bigint(20) NOT NULL COMMENT '主键',
  `sr_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `sm_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`srm_id`),
  KEY `IDX_SRID` (`sr_id`) USING BTREE,
  KEY `IDX_SMID` (`sm_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【配置关系】1、角色-菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `srp_id` bigint(20) NOT NULL COMMENT '主键',
  `sr_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `sp_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`srp_id`),
  KEY `IDX_SRID` (`sr_id`) USING BTREE,
  KEY `IDX_SPID` (`sp_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【配置关系】2、角色-权限表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `su_id` bigint(20) NOT NULL COMMENT '主键',
  `sd_id` bigint(20) DEFAULT NULL COMMENT '所属部门',
  `account` varchar(30) DEFAULT NULL COMMENT '账号',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮件地址',
  `sex` tinyint(4) DEFAULT NULL COMMENT '【性别】1、男性，2、女性',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`su_id`),
  UNIQUE KEY `IDX_ACCOUNT` (`account`) USING BTREE,
  KEY `IDX_SDID` (`sd_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【系统配置】1、用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1000001', '1000001', '645604899', '$2a$10$jqK4Fr8FY.bybX8gNddCIunSqPDKpy3HDa0BOuh/Q.5yvqMjE9rJW', '18826222491', '645604899@qq.com', '1', '2018-12-12', '2018-12-12 15:02:45', '1', '2018-12-12 15:02:55');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `sur_id` bigint(20) NOT NULL COMMENT '主键',
  `su_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `sr_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '【记录状态】0：不可用，1：可用',
  `status_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`sur_id`),
  KEY `IDX_SUID` (`su_id`) USING BTREE,
  KEY `IDX_SRID` (`sr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='【配置关系】3、用户-角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
