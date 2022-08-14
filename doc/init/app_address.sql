-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_address`;
CREATE TABLE `app_address` (
  `user_id` bigint(20) NOT NULL ,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
);

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_address` VALUES ('1', '测试地址1');
INSERT INTO `app_address` VALUES ('2', '测试地址2');