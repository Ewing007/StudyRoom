/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `log_table`;

CREATE TABLE log_table (
                           `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                           `title` varchar(50) DEFAULT '' COMMENT '模块标题',
                           `user_id` VARCHAR(64) NOT NULL COMMENT '用户id',
                           `operation_user` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
                           `content` varchar(100) DEFAULT NULL COMMENT '日志内容',
                           `request_url` varchar(255) DEFAULT '' COMMENT '请求URL',
                           `method` VARCHAR(255) NOT NULL COMMENT '请求的方法名',
                           `request_method` varchar(10) DEFAULT '' COMMENT '请求方式（如：POST, GET等）',
                           `request_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
                           `response_result` varchar(2000) DEFAULT '' COMMENT '方法响应参数',
                           `ip` VARCHAR(128) NOT NULL COMMENT '操作用户的IP地址',
                           `ip_location` varchar(255) DEFAULT '' COMMENT 'IP归属地',
                           `status` char(2) NOT NULL DEFAULT '0' COMMENT '操作状态（1为成功，0为失败）',
                           `error_message` varchar(2000) DEFAULT NULL COMMENT '错误消息',
                           `take_time` bigint(20) DEFAULT NULL COMMENT '方法执行耗时（单位：毫秒）',
                           `operation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间，默认为当前时间'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;