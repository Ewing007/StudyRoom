/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `user_table`;

CREATE TABLE `user_table` (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID自增',
                            `user_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '用户id',
                            `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
                            `password` varchar(255) NOT NULL DEFAULT 'NULL' COMMENT '密码',
                            `status` char(2) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
                            `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                            `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号',
                            `sex` char(2) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
                            `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
                            `credit_score` int(10) DEFAULT 100 COMMENT '信用分',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            UNIQUE KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `sys_user` */

# insert  into `user_table`(`id`,`user_name`,`user_id`,`password`,`type`,`status`,`email`,`phonenumber`,`sex`,`avatar`,`credit_score`,`create_time`,`update_time`,`del_flag`) values
# (14787164048662,'ewing',14787164048662,'$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC','0','0','weixin@qq.com',NULL,NULL,NULL,100,'2022-01-30 17:18:44','2022-01-30 17:18:44',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
