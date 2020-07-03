/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.30-0ubuntu0.18.04.1 : Database - tutor_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `advertisement` */

DROP TABLE IF EXISTS `advertisement`;

CREATE TABLE `advertisement` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名字或标题',
  `type` tinyint(4) DEFAULT '1' COMMENT '类型 1站内跳转 2站外跳转',
  `url` varchar(255) DEFAULT NULL COMMENT '跳转地址或路径',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_tiime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `advertisement` */

insert  into `advertisement`(`id`,`name`,`type`,`url`,`image_url`,`weight`,`status`,`create_tiime`,`update_time`) values (1,'首页大广告1',1,'test','http://qchl2i9x2.bkt.clouddn.com/3d1ac57f06584adc8c2b8e0a4bbd91de.jpg',1,1,'2020-02-19 21:34:05','2020-02-19 21:34:07'),(2,'首页大广告2',1,'test','http://qchl2i9x2.bkt.clouddn.com/2705d2c624ac4d9f88412cf49f56c5f4.jpg',1,1,'2020-02-19 21:34:03','2020-02-19 21:34:08'),(3,'首页大广告3',1,'test','http://qchl2i9x2.bkt.clouddn.com/c79ead6909c74f579def467cc9272c8a.jpg',1,1,'2020-02-19 21:34:02','2020-02-19 21:34:10'),(4,'首页大广告4',2,'test','http://qchl2i9x2.bkt.clouddn.com/4bb791b647da4d02a878aa2f24ca4a7c.jpg',1,1,'2020-02-19 21:34:00','2020-02-19 21:34:12');

/*Table structure for table `advice` */

DROP TABLE IF EXISTS `advice`;

CREATE TABLE `advice` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `advice` */

insert  into `advice`(`id`,`title`,`content`,`weight`,`status`,`create_time`,`update_time`) values (1235166767511977986,NULL,'建议把网站配色改成红色，这样更喜庆。',0,0,NULL,NULL),(1235167249299095553,NULL,'还有王法吗？还有法律吗？这么漂亮的网站你也敢做出来？',0,0,NULL,NULL),(1235166407162544129,NULL,'气死我了，你们网站出现严重BUG，请赶快修复。',0,0,NULL,NULL);

/*Table structure for table `attachment` */

DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `type` varchar(255) DEFAULT NULL COMMENT '后缀',
  `size` bigint(20) DEFAULT NULL COMMENT '大小(字节)',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户id_外键',
  `store_type` tinyint(4) DEFAULT NULL COMMENT '文件储存类型 1本地 2七牛',
  `weight` bigint(20) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='附件表';

/*Data for the table `attachment` */

insert  into `attachment`(`id`,`name`,`type`,`size`,`url`,`user_id`,`store_type`,`weight`,`status`,`create_time`,`update_time`) values (1235502098836795394,'254ae7448a5b49919e137f50483595d3.png','png',33437,'http://qchl2i9x2.bkt.clouddn.com/254ae7448a5b49919e137f50483595d3.png',1,1,NULL,1,NULL,NULL),(1235501876098281473,'0f46d5561085460f94b28532ce719acf.jpg','jpg',44754,'http://qchl2i9x2.bkt.clouddn.com/0f46d5561085460f94b28532ce719acf.jpg',1,1,NULL,1,NULL,NULL),(1235501723639525378,'46b2b724492240b49d9fc4c6ab9071f5.png','png',33437,'http://qchl2i9x2.bkt.clouddn.com/46b2b724492240b49d9fc4c6ab9071f5.png',1,1,NULL,1,NULL,NULL),(1235501723496919041,'9ae2f3d49b47422eb354d8cbed4cad8a.png','png',27114,'http://qchl2i9x2.bkt.clouddn.com/9ae2f3d49b47422eb354d8cbed4cad8a.png',1,1,NULL,1,NULL,NULL),(1235501723291398146,'9d26ece3195b4b2284e6e4d641300b83.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/9d26ece3195b4b2284e6e4d641300b83.png',1,1,NULL,1,NULL,NULL),(1235501615296458753,'fe175a50c9cb43898f69111f4c4c7cdb.png','png',49117,'http://qchl2i9x2.bkt.clouddn.com/fe175a50c9cb43898f69111f4c4c7cdb.png',1,1,NULL,1,NULL,NULL),(1235501615128686593,'0e53deb3047d47bb9f484d7035fecd9b.png','png',16969,'http://qchl2i9x2.bkt.clouddn.com/0e53deb3047d47bb9f484d7035fecd9b.png',1,1,NULL,1,NULL,NULL),(1235500310217797633,'e9c92b20e14f4414b629ce3deaf03a84.jpg','jpg',127868,'http://qchl2i9x2.bkt.clouddn.com/e9c92b20e14f4414b629ce3deaf03a84.jpg',1,1,NULL,1,NULL,NULL),(1235500188348100609,'8c95f6ba929048cf96b4d600234a5f36.png','png',69849,'http://qchl2i9x2.bkt.clouddn.com/8c95f6ba929048cf96b4d600234a5f36.png',1,1,NULL,1,NULL,NULL),(1235500188339712001,'26425d9d09b94d0283639eecdaee525c.png','png',70859,'http://qchl2i9x2.bkt.clouddn.com/26425d9d09b94d0283639eecdaee525c.png',1,1,NULL,1,NULL,NULL),(1235500029459476482,'e1a9aa97583b44299688a2a774081567.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/e1a9aa97583b44299688a2a774081567.png',1,1,NULL,1,NULL,NULL),(1235500029459476483,'1dc1ff4dbaf74e45b04aebd52bfe7c45.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/1dc1ff4dbaf74e45b04aebd52bfe7c45.png',1,1,NULL,1,NULL,NULL),(1235214096629710850,'de6479cc39554393990b0333ea275368.jpg','jpg',178485,'http://qchl2i9x2.bkt.clouddn.com/de6479cc39554393990b0333ea275368.jpg',1,1,NULL,1,NULL,NULL),(1235205121095974914,'c48d931e35124220a67d17f357991a7f.png','png',70859,'http://qchl2i9x2.bkt.clouddn.com/c48d931e35124220a67d17f357991a7f.png',1,1,NULL,1,NULL,NULL),(1235205121083392001,'1a02ec7989a74c85a16e25f2be2117c9.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/1a02ec7989a74c85a16e25f2be2117c9.png',1,1,NULL,1,NULL,NULL),(1235204717633290242,'852754f0929f4a9d9c8da8a223570e9b.png','png',75701,'http://qchl2i9x2.bkt.clouddn.com/852754f0929f4a9d9c8da8a223570e9b.png',1,1,NULL,1,NULL,NULL),(1235204717624901633,'f06a82cc0c334f9c8ceeb904b2c585e5.png','png',40780,'http://qchl2i9x2.bkt.clouddn.com/f06a82cc0c334f9c8ceeb904b2c585e5.png',1,1,NULL,1,NULL,NULL),(1235204717528432642,'90f6933362e34af8a98e8856ba996cf5.png','png',49117,'http://qchl2i9x2.bkt.clouddn.com/90f6933362e34af8a98e8856ba996cf5.png',1,1,NULL,1,NULL,NULL),(1235204717469712386,'b5ec761e9a1d4dadab03862fb3e6bdf0.png','png',33437,'http://qchl2i9x2.bkt.clouddn.com/b5ec761e9a1d4dadab03862fb3e6bdf0.png',1,1,NULL,1,NULL,NULL),(1235204717419380737,'ca08990edade46ac97ab142b756d1553.png','png',16969,'http://qchl2i9x2.bkt.clouddn.com/ca08990edade46ac97ab142b756d1553.png',1,1,NULL,1,NULL,NULL),(1235204590701068289,'c3277c869a7b4e70a3ad66ce92dc4ff3.png','png',49117,'http://qchl2i9x2.bkt.clouddn.com/c3277c869a7b4e70a3ad66ce92dc4ff3.png',1,1,NULL,1,NULL,NULL),(1235204590663319553,'eef2ce6f4d3c495f8b74ebae0028523c.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/eef2ce6f4d3c495f8b74ebae0028523c.png',1,1,NULL,1,NULL,NULL),(1235202250409459713,'3ad071219e3f4f3f8fa9fd0f9cd0cb37.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/3ad071219e3f4f3f8fa9fd0f9cd0cb37.png',1,1,NULL,1,NULL,NULL),(1235202250254270465,'2368a6b802de4988a062fa57b46c7ad2.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/2368a6b802de4988a062fa57b46c7ad2.png',1,1,NULL,1,NULL,NULL),(1235201630470356994,'1adf327c3f0e49618dd0905de01d2bc8.png','png',40780,'http://qchl2i9x2.bkt.clouddn.com/1adf327c3f0e49618dd0905de01d2bc8.png',1,1,NULL,1,NULL,NULL),(1235201630340333570,'db4d8d52f9c84367812ca02921288766.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/db4d8d52f9c84367812ca02921288766.png',1,1,NULL,1,NULL,NULL),(1235201630336139265,'0a232069b13b4527a6e0aee4ed188716.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/0a232069b13b4527a6e0aee4ed188716.png',1,1,NULL,1,NULL,NULL),(1235201630206115842,'01707605b6884f679015add3f7cba75f.png','png',33437,'http://qchl2i9x2.bkt.clouddn.com/01707605b6884f679015add3f7cba75f.png',1,1,NULL,1,NULL,NULL),(1235201630193532930,'abd1c5b4275e406a988d5b1112a9f48e.png','png',27114,'http://qchl2i9x2.bkt.clouddn.com/abd1c5b4275e406a988d5b1112a9f48e.png',1,1,NULL,1,NULL,NULL),(1235201503471026178,'f95b800941074483b19971b9e31ab533.png','png',83944,'http://qchl2i9x2.bkt.clouddn.com/f95b800941074483b19971b9e31ab533.png',1,1,NULL,1,NULL,NULL),(1235201481077637122,'3b8484a510644e1680ab2dbb34634617.png','png',107727,'http://qchl2i9x2.bkt.clouddn.com/3b8484a510644e1680ab2dbb34634617.png',1,1,NULL,1,NULL,NULL),(1235201035273453569,'cbb5593c1629433b949aa2ee29754927.png','png',27114,'http://qchl2i9x2.bkt.clouddn.com/cbb5593c1629433b949aa2ee29754927.png',1,1,NULL,1,NULL,NULL),(1235200984203608065,'007044dcf9d340d4814e7d1cc72e4e73.png','png',16969,'http://qchl2i9x2.bkt.clouddn.com/007044dcf9d340d4814e7d1cc72e4e73.png',1,1,NULL,1,NULL,NULL),(1235196974650310658,'901e358e1dd942ee8f462ad02f2204e4.jpg','jpg',98172,'http://qchl2i9x2.bkt.clouddn.com/901e358e1dd942ee8f462ad02f2204e4.jpg',1,1,NULL,1,NULL,NULL),(1235190798210514945,'861129de3c2646b8b65f25490ff159bd.jpg','jpg',26350,'http://qchl2i9x2.bkt.clouddn.com/861129de3c2646b8b65f25490ff159bd.jpg',1,1,NULL,1,NULL,NULL),(1235185274303758337,'91e1fe6c3b944c47ab5c5586bbe2ae97.jpg','jpg',81362,'http://qchl2i9x2.bkt.clouddn.com/91e1fe6c3b944c47ab5c5586bbe2ae97.jpg',1,1,NULL,1,NULL,NULL),(1235183991316496386,'8fbd433137544eed81a613cb6cb870d3.png','png',23783,'http://qchl2i9x2.bkt.clouddn.com/8fbd433137544eed81a613cb6cb870d3.png',1,1,NULL,1,NULL,NULL),(1235176340910194690,'a76d6a1624734965b06a294379f6678e.jpg','jpg',81362,'http://qchl2i9x2.bkt.clouddn.com/a76d6a1624734965b06a294379f6678e.jpg',1,1,NULL,1,NULL,NULL),(1235174372129062914,'812314458d5a41eda69b010fa61dcf5d.jpeg','jpeg',65662,'http://qchl2i9x2.bkt.clouddn.com/812314458d5a41eda69b010fa61dcf5d.jpeg',1,1,NULL,1,NULL,NULL),(1235173148105326593,'50fa7a4c7b314f69b75d0960461f5d05.jpg','jpg',26350,'http://qchl2i9x2.bkt.clouddn.com/50fa7a4c7b314f69b75d0960461f5d05.jpg',1,1,NULL,1,NULL,NULL),(1235172918798532609,'c2d9be70be09458dbd5097d9c51912da.jpg','jpg',17199,'http://qchl2i9x2.bkt.clouddn.com/c2d9be70be09458dbd5097d9c51912da.jpg',1,1,NULL,1,NULL,NULL),(1235171698637750274,'aa946a27b51a4dfbbebc1dacafe98d3a.jpg','jpg',98172,'http://qchl2i9x2.bkt.clouddn.com/aa946a27b51a4dfbbebc1dacafe98d3a.jpg',1,1,NULL,1,NULL,NULL),(1235170207659147265,'a3af8cbf6a634e058e7f9e1144c9512d.png','png',23783,'http://qchl2i9x2.bkt.clouddn.com/a3af8cbf6a634e058e7f9e1144c9512d.png',1,1,NULL,1,NULL,NULL),(1235163900801662978,'f6c7cb85dbde49fab4182da4106fa51a.png','png',75986,'http://qchl2i9x2.bkt.clouddn.com/f6c7cb85dbde49fab4182da4106fa51a.png',1,1,NULL,1,NULL,NULL),(1235163881814048769,'74e11c272ff8478fb2190951ca9677a1.png','png',107727,'http://qchl2i9x2.bkt.clouddn.com/74e11c272ff8478fb2190951ca9677a1.png',1,1,NULL,1,NULL,NULL),(1235163261484875778,'26a8682a0a6e40f987db482b10f18759.png','png',70323,'http://qchl2i9x2.bkt.clouddn.com/26a8682a0a6e40f987db482b10f18759.png',1,1,NULL,1,NULL,NULL),(1235163238231654402,'ba94d3c2ec3c467ea517296b88ecbdfe.png','png',69849,'http://qchl2i9x2.bkt.clouddn.com/ba94d3c2ec3c467ea517296b88ecbdfe.png',1,1,NULL,1,NULL,NULL),(1235163213686587394,'2286cddf11484e0fb7e4366026e3ca60.png','png',70859,'http://qchl2i9x2.bkt.clouddn.com/2286cddf11484e0fb7e4366026e3ca60.png',1,1,NULL,1,NULL,NULL),(1235162859284676610,'45d8f68ca90b4c52a75d9216f2df05dd.png','png',40780,'http://qchl2i9x2.bkt.clouddn.com/45d8f68ca90b4c52a75d9216f2df05dd.png',1,1,NULL,1,NULL,NULL),(1235162832894115841,'41de66c64ac64db090acfcab8e1e6e90.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/41de66c64ac64db090acfcab8e1e6e90.png',1,1,NULL,1,NULL,NULL),(1235162804393820162,'b1e4081831434a918f426360e9562975.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/b1e4081831434a918f426360e9562975.png',1,1,NULL,1,NULL,NULL),(1235162780930883586,'c3fed318c65e456293f58e7bac1ef36a.png','png',33437,'http://qchl2i9x2.bkt.clouddn.com/c3fed318c65e456293f58e7bac1ef36a.png',1,1,NULL,1,NULL,NULL),(1235162748819292162,'14cbaae8b41d499bad070b1bfaf81025.png','png',27114,'http://qchl2i9x2.bkt.clouddn.com/14cbaae8b41d499bad070b1bfaf81025.png',1,1,NULL,1,NULL,NULL),(1235162120042790913,'a0c8d94ae25f4b0bbb31045a18737671.png','png',49117,'http://qchl2i9x2.bkt.clouddn.com/a0c8d94ae25f4b0bbb31045a18737671.png',1,1,NULL,1,NULL,NULL),(1235162050585116673,'b9c3d892bb9f48348df8a5e8c3e71187.png','png',43643,'http://qchl2i9x2.bkt.clouddn.com/b9c3d892bb9f48348df8a5e8c3e71187.png',1,1,NULL,1,NULL,NULL),(1235155974301106178,'24c9aead3a064f5796c4872e2b3a795f.jpeg','jpeg',19566,'http://qchl2i9x2.bkt.clouddn.com/24c9aead3a064f5796c4872e2b3a795f.jpeg',1,1,NULL,1,NULL,NULL),(1235155958559883265,'3d5d34d39ac14a8eaab91617b66b0ec8.jpeg','jpeg',21368,'http://qchl2i9x2.bkt.clouddn.com/3d5d34d39ac14a8eaab91617b66b0ec8.jpeg',1,1,NULL,1,NULL,NULL),(1235153086514413569,'b666aa3c9d0a46baa8b6acd5323178ac.jpg','jpg',37411,'http://qchl2i9x2.bkt.clouddn.com/b666aa3c9d0a46baa8b6acd5323178ac.jpg',1,1,NULL,1,NULL,NULL),(1230412119576076289,'3d1ac57f06584adc8c2b8e0a4bbd91de.jpg','jpg',127471,'http://qchl2i9x2.bkt.clouddn.com/3d1ac57f06584adc8c2b8e0a4bbd91de.jpg',1,1,NULL,1,NULL,NULL),(1230412298165346305,'2705d2c624ac4d9f88412cf49f56c5f4.jpg','jpg',172987,'http://qchl2i9x2.bkt.clouddn.com/2705d2c624ac4d9f88412cf49f56c5f4.jpg',1,1,NULL,1,NULL,NULL),(1230412443003052034,'c79ead6909c74f579def467cc9272c8a.jpg','jpg',319061,'http://qchl2i9x2.bkt.clouddn.com/c79ead6909c74f579def467cc9272c8a.jpg',1,1,NULL,1,NULL,NULL),(1230412562683322370,'4bb791b647da4d02a878aa2f24ca4a7c.jpg','jpg',275972,'http://qchl2i9x2.bkt.clouddn.com/4bb791b647da4d02a878aa2f24ca4a7c.jpg',1,1,NULL,1,NULL,NULL),(1234491114718420994,'41857a6de7414493a0a4067fa166427e.png','png',6840,'http://qchl2i9x2.bkt.clouddn.com/41857a6de7414493a0a4067fa166427e.png',1,1,NULL,1,NULL,NULL),(1234491693406543874,'b9a6606c54ac4785a10ce2521161eccd.png','png',1762,'http://qchl2i9x2.bkt.clouddn.com/b9a6606c54ac4785a10ce2521161eccd.png',1,1,NULL,1,NULL,NULL),(1234491715883819010,'82c82f1f20124ba996f6084e9e1fd1b0.jpeg','jpeg',15926,'http://qchl2i9x2.bkt.clouddn.com/82c82f1f20124ba996f6084e9e1fd1b0.jpeg',1,1,NULL,1,NULL,NULL),(1234491095806304258,'f451e99b61704932be579e13b46517a1.png','png',6840,'http://qchl2i9x2.bkt.clouddn.com/f451e99b61704932be579e13b46517a1.png',1,1,NULL,1,NULL,NULL),(1234490144726900738,'2a720c7dfb4b43b9b900430c9374cb3c.jpeg','jpeg',15926,'http://qchl2i9x2.bkt.clouddn.com/2a720c7dfb4b43b9b900430c9374cb3c.jpeg',1,1,NULL,1,NULL,NULL),(1234490130780839938,'dada14746956408aa643f505480a7bf8.jpg','jpg',17199,'http://qchl2i9x2.bkt.clouddn.com/dada14746956408aa643f505480a7bf8.jpg',1,1,NULL,1,NULL,NULL),(1234436815002984449,'3851a80004fb4ec7a42399f05421dd3a.jpg','jpg',17199,'http://qchl2i9x2.bkt.clouddn.com/3851a80004fb4ec7a42399f05421dd3a.jpg',1,1,NULL,1,NULL,NULL),(1234436779171045378,'c8876610ecd845bda2257b34779dd220.jpeg','jpeg',15926,'http://qchl2i9x2.bkt.clouddn.com/c8876610ecd845bda2257b34779dd220.jpeg',1,1,NULL,1,NULL,NULL),(1234404988007669762,'898049962bf042ab9d0524354b7a1104.jpeg','jpeg',15926,'http://qchl2i9x2.bkt.clouddn.com/898049962bf042ab9d0524354b7a1104.jpeg',1,1,NULL,1,NULL,NULL),(1234404939836088322,'fda5cc23b550401896a61ba7f080340e.jpg','jpg',17199,'http://qchl2i9x2.bkt.clouddn.com/fda5cc23b550401896a61ba7f080340e.jpg',1,1,NULL,1,NULL,NULL),(1233693421423251457,'330078ce6db84f6890bf3205cfa9cd7a.png','png',1984,'http://qchl2i9x2.bkt.clouddn.com/330078ce6db84f6890bf3205cfa9cd7a.png',1,1,NULL,1,NULL,NULL),(1233693332764053505,'c873013485634996b547c457f658daa2.png','png',2427,'http://qchl2i9x2.bkt.clouddn.com/c873013485634996b547c457f658daa2.png',1,1,NULL,1,NULL,NULL),(1233693191004966913,'be983326c8fc4411bde4a2952f265b24.png','png',4685,'http://qchl2i9x2.bkt.clouddn.com/be983326c8fc4411bde4a2952f265b24.png',1,1,NULL,1,NULL,NULL),(1233314243137937409,'3fe01e4cdcc94b70a8a67c65e7774126.jpg','jpg',8747,'http://qchl2i9x2.bkt.clouddn.com/3fe01e4cdcc94b70a8a67c65e7774126.jpg',1,1,NULL,1,NULL,NULL),(1233314189891248129,'6b5131772fd3451f9c12bba1fad788c6.jpg','jpg',7700,'http://qchl2i9x2.bkt.clouddn.com/6b5131772fd3451f9c12bba1fad788c6.jpg',1,1,NULL,1,NULL,NULL),(1233313901780312066,'bda6e1abb2b34f12b9caa174f891df47.jpg','jpg',8747,'http://qchl2i9x2.bkt.clouddn.com/bda6e1abb2b34f12b9caa174f891df47.jpg',1,1,NULL,1,NULL,NULL),(1230471280057835521,'38bc84909acd4059a21fd7a33de266ac.jpg','jpg',7198,'http://qchl2i9x2.bkt.clouddn.com/38bc84909acd4059a21fd7a33de266ac.jpg',1,1,NULL,1,NULL,NULL),(1230468259022299138,'c6e4a627fae844339a72c3be6cafea2a.jpg','jpg',7198,'http://qchl2i9x2.bkt.clouddn.com/c6e4a627fae844339a72c3be6cafea2a.jpg',1,1,NULL,1,NULL,NULL),(1230427222681772033,'28c12af00200469bb3bff750afb8c6fd.jpg','jpg',10072,'http://qchl2i9x2.bkt.clouddn.com/28c12af00200469bb3bff750afb8c6fd.jpg',1,1,NULL,1,NULL,NULL),(1230427009493688322,'da3b5286bb10460f8d87e7ed54ef2b20.jpg','jpg',10072,'http://qchl2i9x2.bkt.clouddn.com/da3b5286bb10460f8d87e7ed54ef2b20.jpg',1,1,NULL,1,NULL,NULL),(1230414614553616385,'40ec3cda30e74666a4b75699f1ae948b.png','png',7634,'http://qchl2i9x2.bkt.clouddn.com/40ec3cda30e74666a4b75699f1ae948b.png',1,1,NULL,1,NULL,NULL),(1230414569146081281,'85415f68d62d43d2a77572cda90e972b.png','png',7634,'http://qchl2i9x2.bkt.clouddn.com/85415f68d62d43d2a77572cda90e972b.png',1,1,NULL,1,NULL,NULL),(1230413397563727874,'7573fc25776c4117bf1a175fd4f7a661.jpg','jpg',17199,'http://qchl2i9x2.bkt.clouddn.com/7573fc25776c4117bf1a175fd4f7a661.jpg',1,1,NULL,1,NULL,NULL),(1235502098836795395,'b453aaebdba24000b8d59b875ec92a8f.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/b453aaebdba24000b8d59b875ec92a8f.png',1,1,NULL,1,NULL,NULL),(1235502098882932738,'8cbe1c7682f24dbb8e95e243c130a013.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/8cbe1c7682f24dbb8e95e243c130a013.png',1,1,NULL,1,NULL,NULL),(1235504324137365506,'8279da708cd7458c833010b0628882c2.png','png',49117,'http://qchl2i9x2.bkt.clouddn.com/8279da708cd7458c833010b0628882c2.png',1,1,NULL,1,NULL,NULL),(1235504324238028802,'21ae035048c54e1f95acabb3297c8baa.png','png',75701,'http://qchl2i9x2.bkt.clouddn.com/21ae035048c54e1f95acabb3297c8baa.png',1,1,NULL,1,NULL,NULL),(1235504474444443650,'b3df75fce5d94750a7b867160a4fd097.png','png',107727,'http://qchl2i9x2.bkt.clouddn.com/b3df75fce5d94750a7b867160a4fd097.png',1,1,NULL,1,NULL,NULL),(1235504474545106946,'e8a8276d63a24ac1a8e3f1ce23bf4842.png','png',83944,'http://qchl2i9x2.bkt.clouddn.com/e8a8276d63a24ac1a8e3f1ce23bf4842.png',1,1,NULL,1,NULL,NULL),(1235504568082280449,'744fbf91ff0843608978b6dd4c00db9a.png','png',16969,'http://qchl2i9x2.bkt.clouddn.com/744fbf91ff0843608978b6dd4c00db9a.png',1,1,NULL,1,NULL,NULL),(1235504568145195010,'708f51901c6c475ebac1a6b59b53edc6.png','png',49117,'http://qchl2i9x2.bkt.clouddn.com/708f51901c6c475ebac1a6b59b53edc6.png',1,1,NULL,1,NULL,NULL),(1235504568296189954,'b3d402e897c644ee9cc05be2bec45bd0.png','png',43643,'http://qchl2i9x2.bkt.clouddn.com/b3d402e897c644ee9cc05be2bec45bd0.png',1,1,NULL,1,NULL,NULL),(1235504645085507585,'037716f6ff4344d79c6061b06d69cf3a.jpg','jpg',70299,'http://qchl2i9x2.bkt.clouddn.com/037716f6ff4344d79c6061b06d69cf3a.jpg',1,1,NULL,1,NULL,NULL),(1235509939505115137,'2a30ace42d2e4460860c255d91eeb978.jpg','jpg',70299,'http://qchl2i9x2.bkt.clouddn.com/2a30ace42d2e4460860c255d91eeb978.jpg',1,1,NULL,1,NULL,NULL),(1235510149975289858,'b7c48bf56f834806b8b619f2f4daafa1.png','png',16969,'http://qchl2i9x2.bkt.clouddn.com/b7c48bf56f834806b8b619f2f4daafa1.png',1,1,NULL,1,NULL,NULL),(1235510150323417089,'739462164dfa4b79a62be75f20439f8c.png','png',75701,'http://qchl2i9x2.bkt.clouddn.com/739462164dfa4b79a62be75f20439f8c.png',1,1,NULL,1,NULL,NULL),(1235510331760619522,'bcf53ee2f36641548d140e66c434307e.png','png',38193,'http://qchl2i9x2.bkt.clouddn.com/bcf53ee2f36641548d140e66c434307e.png',1,1,NULL,1,NULL,NULL),(1235510331852894210,'1b8e00b1e55848979008d2087a196af6.png','png',33437,'http://qchl2i9x2.bkt.clouddn.com/1b8e00b1e55848979008d2087a196af6.png',1,1,NULL,1,NULL,NULL),(1235510331865477121,'81e7df932062418bb329e2873dd7ad59.png','png',27114,'http://qchl2i9x2.bkt.clouddn.com/81e7df932062418bb329e2873dd7ad59.png',1,1,NULL,1,NULL,NULL),(1235510331995500545,'f3379703a6774594990dc14b96fc733a.png','png',40320,'http://qchl2i9x2.bkt.clouddn.com/f3379703a6774594990dc14b96fc733a.png',1,1,NULL,1,NULL,NULL),(1235510762884739073,'bfccdcb8b91e4fdf89b6f4de4347c939.png','png',70859,'http://qchl2i9x2.bkt.clouddn.com/bfccdcb8b91e4fdf89b6f4de4347c939.png',1,1,NULL,1,NULL,NULL),(1235510762993790977,'2e2ba7ac9c8743be93a99c5dad216e6d.png','png',69849,'http://qchl2i9x2.bkt.clouddn.com/2e2ba7ac9c8743be93a99c5dad216e6d.png',1,1,NULL,1,NULL,NULL),(1235624508294643713,'7360e98971214ab0aa7d3da19bfb738f.png','png',9031,'http://qchl2i9x2.bkt.clouddn.com/7360e98971214ab0aa7d3da19bfb738f.png',NULL,1,NULL,1,NULL,NULL),(1277215773452615682,'afa3e65901a74b7cb8b0961e10dc62e1.png','png',853601,'http://qchl2i9x2.bkt.clouddn.com/afa3e65901a74b7cb8b0961e10dc62e1.png',NULL,1,NULL,1,NULL,NULL);

/*Table structure for table `config` */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `key` varchar(255) DEFAULT NULL COMMENT '键',
  `value` varchar(255) DEFAULT NULL COMMENT '值',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `config` */

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'id',
  `dict_type_id` bigint(20) unsigned NOT NULL COMMENT '字典类型id_外键',
  `name` varchar(255) DEFAULT NULL COMMENT '字典值 如 男',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='字典表';

/*Data for the table `dict` */

insert  into `dict`(`id`,`dict_type_id`,`name`,`weight`,`status`,`create_time`,`update_time`) values (1,1,'零基础',NULL,NULL,NULL,NULL),(2,1,'补差型',NULL,NULL,NULL,NULL),(3,1,'拔尖型',NULL,NULL,NULL,NULL),(4,2,'在校大学生',NULL,NULL,NULL,NULL),(5,2,'在职老师',NULL,NULL,NULL,NULL),(6,2,'其他',NULL,NULL,NULL,NULL),(0,2,'测试',NULL,NULL,NULL,NULL);

/*Table structure for table `dict_type` */

DROP TABLE IF EXISTS `dict_type`;

CREATE TABLE `dict_type` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'id',
  `code` varchar(255) DEFAULT NULL COMMENT '类型唯一,且一般不能修改 如 gender 代表性别',
  `name` varchar(255) DEFAULT NULL COMMENT '名字 展示用, 如性别(可修改) ',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='字典类型表';

/*Data for the table `dict_type` */

insert  into `dict_type`(`id`,`code`,`name`,`weight`,`status`,`create_time`,`update_time`) values (1,'studentType','学生类型',NULL,NULL,NULL,NULL),(2,'teacherType','教师类型',NULL,NULL,NULL,NULL);

/*Table structure for table `friend_link` */

DROP TABLE IF EXISTS `friend_link`;

CREATE TABLE `friend_link` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `url` varchar(255) DEFAULT NULL COMMENT '跳转url',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `friend_link` */

insert  into `friend_link`(`id`,`name`,`url`,`image_url`,`weight`,`status`,`create_time`,`update_time`) values (1,'闽南师大','http://www.mnnu.edu.cn/','http://qchl2i9x2.bkt.clouddn.com/85415f68d62d43d2a77572cda90e972b.png',2,1,NULL,NULL),(2,'计算机学院','http://cs.mnnu.edu.cn/','http://qchl2i9x2.bkt.clouddn.com/40ec3cda30e74666a4b75699f1ae948b.png',NULL,1,NULL,NULL);

/*Table structure for table `navigation` */

DROP TABLE IF EXISTS `navigation`;

CREATE TABLE `navigation` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父id_外键',
  `name` varchar(255) DEFAULT NULL COMMENT '导航名字',
  `url` varchar(255) DEFAULT NULL COMMENT '导航路径',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1站内 2站外',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `navigation` */

insert  into `navigation`(`id`,`parent_id`,`name`,`url`,`type`,`weight`,`status`,`create_time`,`update_time`) values (1,0,'首页','/index',1,NULL,1,NULL,NULL),(2,0,'请家教','/needs/add',1,NULL,1,NULL,NULL),(3,0,'做家教','/needs',1,NULL,1,NULL,NULL),(4,0,'教员库','/teachers',1,NULL,1,NULL,NULL),(5,0,'家教价格',NULL,NULL,NULL,1,NULL,NULL),(6,0,'家教须知',NULL,NULL,NULL,1,NULL,NULL),(7,0,'家教学堂',NULL,NULL,NULL,1,NULL,NULL),(8,0,'关于我们',NULL,NULL,NULL,1,NULL,NULL);

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `weight` bigint(20) DEFAULT '0' COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0禁用 1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `notice` */

insert  into `notice`(`id`,`title`,`content`,`weight`,`status`,`create_time`,`update_time`) values (1235145067667865601,'网站维护通告','因网站数据迁移和主机升级，需在2020年3月18日至3月19日期间进行系统维护，维护时间为3月18日晚上17:30至3月19日24：00，请各用户在维护期间暂停使用本系统，以免造成不必要的损失。对于系统维护给您带来的不便，敬请谅解，感谢大家的支持和配合。',0,1,NULL,NULL),(1234802596559667202,'小葵花妈妈课堂开课了','小葵花妈妈课堂开课啦，孩子咳嗽老不好，怎么办？多半是不想上学装的，打一顿就好。',0,1,NULL,NULL),(1235146615894523906,'特大消息！特大消息！','福建漳州，福建漳州，闽师家教倒闭了，王八蛋老板欠下3.5个亿带着他的小姨子跑路了……',0,1,NULL,NULL),(1235147240287977473,'新春祝福','新春将至，闽师家教的全体员工，在这里恭祝大家新年快乐，学生学业有成！',0,1,NULL,NULL);

/*Table structure for table `route` */

DROP TABLE IF EXISTS `route`;

CREATE TABLE `route` (
  `id` bigint(20) unsigned NOT NULL,
  `parent_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `component_path` varchar(255) DEFAULT NULL,
  `weight` bigint(20) DEFAULT '0',
  `status` tinyint(4) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `route` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
