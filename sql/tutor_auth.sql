/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.30-0ubuntu0.18.04.1 : Database - tutor_auth
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `clientdetails` */

DROP TABLE IF EXISTS `clientdetails`;

CREATE TABLE `clientdetails` (
  `appId` varchar(128) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `clientdetails` */

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_approvals` */

DROP TABLE IF EXISTS `oauth_approvals`;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` datetime DEFAULT NULL,
  `lastModifiedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_approvals` */

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('auth',NULL,'$2a$10$k8j86vC/4qWOrgxHzZRK6eTOk4TnZIJsZa02gmZjj2i9XhnUcTlg6','all','authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social',NULL,NULL,NULL,NULL,NULL,'true'),('business',NULL,'$2a$10$Q2u43en2yIjHZHjaWuhCnOKWV1PmuAWVDXS.cfK0XQhxSOsHL7dFy',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code',NULL,NULL,NULL,NULL,NULL,NULL),('gateway',NULL,'$2a$10$p6WC6UGY/JJPWoOlJsoAr.W6NdccYGR1cOfzX6olS75fX7hV2tENq',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social',NULL,NULL,NULL,NULL,NULL,NULL),('pay',NULL,'$2a$10$iS17RtdeVbrc70Hn4IsMXO5CyI46ztu0lxyce29fOMlmsawwnuywy',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social',NULL,NULL,NULL,NULL,NULL,NULL),('system',NULL,'$2a$10$8bOM5ltVIH6RfCuaAKpa2O0BMwTnKXm8AIwmy1XfprZ0KU0/P.bam',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social',NULL,NULL,NULL,NULL,NULL,NULL),('upms',NULL,'$2a$10$P4m8trxC0bH53xgYI5dKouiGcJvhXSyzO4ILp5V.OY3mAL/xNLvSS',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social',NULL,NULL,NULL,NULL,NULL,NULL),('web-admin',NULL,'$2a$10$aEbbwXNs5ualcfykRL2IWOsdVZROb16yvxgxCXvzt/zBQh5XUfWxC',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social',NULL,NULL,NULL,NULL,NULL,NULL),('web-portal',NULL,'$2a$10$uncALAp.7lHrku4CU2H8Ce6NWDdHxyGGB85nZraZkQ.KKEhZ66tiW',NULL,'authorization_code,password,client_credentials,implicit,refresh_token,sms_code,social','http://tutor-portal-web:1463/ssoredirect,http://47.115.38.150:4591/ssoredirect',NULL,NULL,NULL,NULL,'true');

/*Table structure for table `oauth_client_token` */

DROP TABLE IF EXISTS `oauth_client_token`;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `userconnection` */

DROP TABLE IF EXISTS `userconnection`;

CREATE TABLE `userconnection` (
  `userId` varchar(128) NOT NULL,
  `providerId` varchar(128) NOT NULL,
  `providerUserId` varchar(128) NOT NULL DEFAULT '',
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(512) NOT NULL,
  `secret` varchar(512) DEFAULT NULL,
  `refreshToken` varchar(512) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `userconnection` */

insert  into `userconnection`(`userId`,`providerId`,`providerUserId`,`rank`,`displayName`,`profileUrl`,`imageUrl`,`accessToken`,`secret`,`refreshToken`,`expireTime`) values ('1235151262487101442','qq','3E8D6171D7EC7CF2334858DDE55F0A25',1,'juchia',NULL,'http://thirdqq.qlogo.cn/g?b=oidb&k=5lfwPfEBMUNXdxqR2XV54A&s=100&t=1483350594','81C8E73738F55362BCEF459EA5432D5E',NULL,'892E8BFD7B5FC4C66B6315BAF4A23B32',1601093295910);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
