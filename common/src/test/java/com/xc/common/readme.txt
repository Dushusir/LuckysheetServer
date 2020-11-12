-- 测试表
CREATE TABLE `test_db` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '中文名',
  `PASSWORD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `EMAIL` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `TEL` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改用户',
  `CREATE_TM` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TM` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否删除：0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


com.mysql.jdbc.Driver 是 mysql-connector-java 5中的，
com.mysql.cj.jdbc.Driver 是 mysql-connector-java 6中的
1、JDBC连接Mysql5 com.mysql.jdbc.Driver:
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false
username=root
password=
2、JDBC连接Mysql6 com.mysql.cj.jdbc.Driver， 需要指定时区serverTimezone:
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
username=root
password=