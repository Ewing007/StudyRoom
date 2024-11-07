/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `room_table` */

DROP TABLE IF EXISTS `room_table`;


# CREATE TABLE room_table (
#                             `id` BIGINT(20) AUTO_INCREMENT COMMENT '主键ID自增主键',
#                             `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID',
#                             `room_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '自习室ID',
#                             `name` VARCHAR(64) NOT NULL COMMENT '自习室名称',
#                             `location` VARCHAR(255) NOT NULL COMMENT '自习室位置描述',
#                             `capacity` INT(10) NOT NULL COMMENT '自习室容量',
#                             `amenities` VARCHAR(255) DEFAULT NULL COMMENT '自习室设施',
#                             `current_status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '当前状态（0-开放、1-维护中、2-关闭）' ,
#                             `opening_hours` VARCHAR(64) NOT NULL COMMENT '开放时间',
#                             `description` VARCHAR(255) DEFAULT NULL COMMENT '自习室描述',
#                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
#                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
#                             `floor` CHAR(10) NOT NULL COMMENT '所在楼层',
#                             `type` CHAR(2) NOT NULL DEFAULT '0' COMMENT '自习室类型(0-免费、1-收费)',
#                             `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
#                             `image_url` VARCHAR(255) DEFAULT NULL COMMENT '自习室图片URL',
#                             UNIQUE KEY(id)
# ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='自习室表';

CREATE TABLE room_table (
                            `id` BIGINT(20) AUTO_INCREMENT COMMENT '主键ID自增主键',
                            `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID',
                            `room_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '自习室ID',
                            `name` VARCHAR(64) NOT NULL COMMENT '自习室名称',
                            `location` VARCHAR(255) NOT NULL COMMENT '自习室位置描述',
                            `capacity` INT(10) NOT NULL COMMENT '自习室容量',
                            `amenities` VARCHAR(255) DEFAULT NULL COMMENT '自习室设施',
                            `current_status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '当前状态（0-开放、1-维护中、2-关闭）',
                            `description` VARCHAR(255) DEFAULT NULL COMMENT '自习室描述',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `floor` CHAR(10) NOT NULL COMMENT '所在楼层',
                            `type` CHAR(2) NOT NULL DEFAULT '0' COMMENT '自习室类型(0-免费、1-收费)',
                            `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            `image_url` VARCHAR(255) DEFAULT NULL COMMENT '自习室图片URL',
                            UNIQUE KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='自习室表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;