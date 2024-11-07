/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `lost_found_table` */

DROP TABLE IF EXISTS `lost_found_table`;

CREATE TABLE lost_found_table (
                            `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT COMMENT '信息的唯一标识',
                            `user_id` VARCHAR(64)  NOT NULL COMMENT '发布信息的用户 ID，外键关联用户表',
                            `item_type` VARCHAR(50) NOT NULL COMMENT '物品类型（失物或招领）',
                            `description` TEXT NOT NULL COMMENT '物品描述',
                            `status` CHAR(1) DEFAULT 1 COMMENT '信息状态，1-表示有效，0-表示已处理',
                            `location` VARCHAR(255) NOT NULL COMMENT '遗失或招领地点',
                            `image_url` VARCHAR(255) DEFAULT NULL COMMENT '物品图片URL',
                            `contact_info` VARCHAR(255) NOT NULL COMMENT '联系方式',
                            `illegal` CHAR(2) DEFAULT '0' COMMENT '是否违规（0-否，1-是）',
                            `del_illegal` CHAR(2) DEFAULT '0' COMMENT '是否删除（0-否，1-是）',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '信息创建时间',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '信息更新时间',
                            FOREIGN KEY (user_id) REFERENCES user_table(user_id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT '失物遗失招领表';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;