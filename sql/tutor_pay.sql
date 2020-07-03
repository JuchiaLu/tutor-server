/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.30-0ubuntu0.18.04.1 : Database - tutor_pay
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `cashin` */

DROP TABLE IF EXISTS `cashin`;

CREATE TABLE `cashin` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `need_id` bigint(20) DEFAULT NULL COMMENT 'needID_外键',
  `appoint_id` bigint(20) DEFAULT NULL COMMENT 'appointID_外键预留',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID_外键',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '收入金额',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '余额',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `cteate_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `cashin` */

insert  into `cashin`(`id`,`need_id`,`appoint_id`,`user_id`,`total_amount`,`balance`,`name`,`description`,`status`,`weight`,`cteate_time`,`update_time`) values (1238766713423179778,1238765302106976257,1238765506193420290,1235151262487101442,'900.00','2094.00','上门家教：答辩测试','家教地址：漳州市 龙文区 小区名',1,0,'2020-03-14 17:59:33',NULL),(1235591654122696706,1235549308051984385,1235578730813530114,1235151262487101442,'414.00','1194.00','上门家教：测试','家教地址：漳州市 芗城区 达理活动中心',1,0,'2020-03-05 23:43:01',NULL),(1235608578508386306,NULL,NULL,1235151262487101442,'4.00','1194.00','提现失败返还到余额','姓名与账号不匹配',1,0,'2020-03-06 00:50:15',NULL),(1235229938029404162,NULL,NULL,1235151262487101442,'40.00','780.00','提现失败返还到余额','姓名填写错误',1,0,'2020-03-04 23:45:41',NULL),(1235228122256482306,1235225008912654338,1235226709472239618,1235151262487101442,'800.00','800.00','上门家教：广同学','家教地址：银河广场4楼3号',1,0,'2020-03-04 23:38:27',NULL),(1238767573196779521,NULL,NULL,1235151262487101442,'100.00','1994.00','提现失败返还到余额','姓名错误',1,0,'2020-03-14 18:02:59',NULL),(1242751280022794242,1242747556541947905,1242750164014915585,1235151262487101442,'300.00','2294.00','上门家教：test','家教地址：漳州市 芗城区 小区名',1,0,'2020-03-25 17:52:48',NULL),(1242783196839129090,1242781980054708225,1242782276864630786,1235151262487101442,'600.00','2794.00','上门家教：最终答辩','家教地址：漳州市 平和县 小区名',1,0,'2020-03-25 19:59:38',NULL);

/*Table structure for table `cashout` */

DROP TABLE IF EXISTS `cashout`;

CREATE TABLE `cashout` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id_外键',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '系统支付类型 1支付宝 2微信 3网银',
  `pay_account` varchar(255) DEFAULT NULL COMMENT '系统支付帐号',
  `pay_no` varchar(255) DEFAULT NULL COMMENT '系统支付流水号',
  `platform_trade_no` varchar(255) DEFAULT NULL COMMENT '平台支付流水号',
  `cash` decimal(10,2) DEFAULT NULL COMMENT '请求提现金额',
  `fees` decimal(10,2) DEFAULT NULL COMMENT '提现手续金额',
  `real_cash` decimal(10,2) DEFAULT NULL COMMENT '提现到手金额',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '余额',
  `cashout_type` tinyint(4) DEFAULT '1' COMMENT '提现类型 1支付宝 2微信 3银行卡',
  `realname` varchar(255) DEFAULT NULL COMMENT '姓名',
  `cashout_account` varchar(255) DEFAULT NULL COMMENT '提现帐号',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态 1处理中 2成功 3失败',
  `reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `cashout` */

insert  into `cashout`(`id`,`user_id`,`pay_type`,`pay_account`,`pay_no`,`platform_trade_no`,`cash`,`fees`,`real_cash`,`balance`,`cashout_type`,`realname`,`cashout_account`,`note`,`state`,`reason`,`finish_time`,`weight`,`status`,`create_time`,`update_time`) values (1235229534893875202,1235151262487101442,NULL,NULL,NULL,NULL,'20.00',NULL,NULL,'780.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-03-04 23:44:05',NULL),(1235229842588016642,1235151262487101442,NULL,NULL,NULL,NULL,'40.00',NULL,NULL,'740.00',1,'沙箱环境2','aubhsx3836@sandbox.com','测试',2,'姓名填写错误',NULL,0,1,'2020-03-04 23:45:18',NULL),(1235604918609051650,1235151262487101442,NULL,NULL,NULL,NULL,'4.00',NULL,NULL,'1190.00',1,'沙箱环境2','aubhsx3836@sandbox.com','测试',2,'姓名与账号不匹配',NULL,0,1,'2020-03-06 00:35:42',NULL),(1238767277020196865,1235151262487101442,NULL,NULL,NULL,NULL,'100.00',NULL,NULL,'1994.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-03-14 18:01:48',NULL),(1238767441235587074,1235151262487101442,NULL,NULL,NULL,NULL,'100.00',NULL,NULL,'1894.00',1,'沙箱环境2','aubhsx3836@sandbox.com','测试',2,'姓名错误',NULL,0,1,'2020-03-14 18:02:27',NULL),(1242752386098507778,1235151262487101442,NULL,NULL,NULL,NULL,'100.00',NULL,NULL,'2194.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-03-25 17:57:12',NULL),(1242784529352732673,1235151262487101442,NULL,NULL,NULL,NULL,'100.00',NULL,NULL,'2694.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-03-25 20:04:56',NULL),(1277091480446054402,1235151262487101442,NULL,NULL,NULL,NULL,'100.00',NULL,NULL,'2594.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-06-28 12:08:29',NULL),(1277100596015095810,1235151262487101442,NULL,NULL,NULL,NULL,'10.00',NULL,NULL,'2584.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-06-28 12:44:43',NULL),(1277100614520365058,1235151262487101442,NULL,NULL,NULL,NULL,'10.00',NULL,NULL,'2574.00',1,'沙箱环境2','aubhsx3836@sandbox.com','测试',1,NULL,NULL,0,1,'2020-06-28 12:44:48',NULL),(1277281647606956034,1235151262487101442,NULL,NULL,NULL,NULL,'1.00',NULL,NULL,'2573.00',1,'沙箱环境','aubhsx3836@sandbox.com','测试',3,NULL,NULL,0,1,'2020-06-29 00:44:09',NULL);

/*Table structure for table `financial` */

DROP TABLE IF EXISTS `financial`;

CREATE TABLE `financial` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户Id_外键',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1收入 2支出',
  `cash` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `financial` */

/*Table structure for table `pay_info` */

DROP TABLE IF EXISTS `pay_info`;

CREATE TABLE `pay_info` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `appoint_id` bigint(20) DEFAULT NULL COMMENT '预约ID_外键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID_外键',
  `to_id` bigint(20) DEFAULT NULL COMMENT '付款给老师的id',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1支付宝 2微信',
  `state` tinyint(4) DEFAULT NULL COMMENT '支付状态 1未支付 2已支付 3已退款 4已关闭',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '订单号',
  `platform_trade_no` varchar(255) DEFAULT NULL COMMENT '支付平台流水号',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `name` varchar(255) DEFAULT NULL COMMENT '订单名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_reason` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `time_expire` datetime DEFAULT NULL COMMENT '订单绝对超时时间',
  `timeout_express` varchar(255) DEFAULT NULL COMMENT '订单相对超时时间表达式',
  `request_no` varchar(255) DEFAULT NULL COMMENT '退款请求号',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间(支付后退款)',
  `close_time` datetime DEFAULT NULL COMMENT '订单关闭时间(创建后不支付或超时)',
  `success_time` datetime DEFAULT NULL COMMENT '订单成功时间(付款成功后)',
  `finish_time` datetime DEFAULT NULL COMMENT '订单完成时间(超过退款时间后)',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `cteate_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `pay_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
