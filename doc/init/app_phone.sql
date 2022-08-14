-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_phone`;
CREATE TABLE `app_phone` (
  `user_id` bigint(20) NOT NULL ,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
);

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_phone` VALUES ('1', '123456');
INSERT INTO `app_phone` VALUES ('2', '1234567');