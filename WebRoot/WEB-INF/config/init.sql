/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `authorities` */

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `authorities` */

insert  into `authorities`(`username`,`authority`) values ('admin','ROLE_PLATFORMADMIN'),('admin','ROLE_SYSADMIN'),('lxb','ROLE_LOGIN'),('lxb','ROLE_LOGINTOWELCOME'),('test','ROLE_LOGIN'),('test','ROLE_SERVICE'),('user','ROLE_LOGIN'),('user','ROLE_USER');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `roleid` varchar(64) NOT NULL,
  `roleno` varchar(5) NOT NULL,
  `rolename` varchar(100) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`roleid`,`roleno`,`rolename`,`remark`) values ('00000000001','10000','admin','系统管理员'),('00000000002','10001','login','登录用户'),('00000000003','10002','user','普通用户');

/*Table structure for table `rolerule` */

DROP TABLE IF EXISTS `rolerule`;

CREATE TABLE `rolerule` (
  `roleid` varchar(64) DEFAULT NULL,
  `ruleid` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rolerule` */

insert  into `rolerule`(`roleid`,`ruleid`) values ('00000000001','0000001'),('00000000002','0000003'),('00000000003','0000003'),('00000000003','0000002');

/*Table structure for table `rule` */

DROP TABLE IF EXISTS `rule`;

CREATE TABLE `rule` (
  `ruleid` varchar(64) NOT NULL,
  `ruleno` varchar(5) NOT NULL,
  `rulename` varchar(100) NOT NULL,
  `url` varchar(500) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ruleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rule` */

insert  into `rule`(`ruleid`,`ruleno`,`rulename`,`url`,`remark`) values ('0000001','10000','后台管理','/jsp/admin/*',NULL),('0000002','10001','普通用户','/jsp/user/*',NULL),('0000003','10001','登录用户','/jsp/login/*',NULL);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` varchar(64) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`age`,`address`,`enabled`,`password`) values ('01','小明',10,'那里',1,'100623772a71875eb3e46b7ecc2b78e5'),('11','小李',11,'那里',1,'456'),('21','小王',12,'那里',1,'789'),('31','小张',13,'那里',1,'101'),('41','小赵',14,'那里',1,'101');

/*Table structure for table `userrole` */

DROP TABLE IF EXISTS `userrole`;

CREATE TABLE `userrole` (
  `roleid` varchar(64) NOT NULL,
  `userid` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `userrole` */

insert  into `userrole`(`roleid`,`userid`) values ('00000000001','01'),('00000000002','11'),('00000000003','21');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`) values ('admin','ceb4f32325eda6142bd65215f4c0f371',1),('lxb','c7d3f4c857bc8c145d6e5d40c1bf23d9',1),('test','098f6bcd4621d373cade4e832627b4f6',1),('user','47a733d60998c719cf3526ae7d106d13',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
