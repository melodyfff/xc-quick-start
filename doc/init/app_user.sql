-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `sex` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES ('1', '123456', 'admin','MAN');
INSERT INTO `app_user` VALUES ('2', '123456', 'user2','MAN');
INSERT INTO `app_user` VALUES ('3', '123456', 'user3','MAN');
INSERT INTO `app_user` VALUES ('4', '123456', 'user4','MAN');
INSERT INTO `app_user` VALUES ('5', '123456', 'user5','MAN');
