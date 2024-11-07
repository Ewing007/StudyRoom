/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `message_table` */

DROP TABLE IF EXISTS `permission_table`;

CREATE TABLE `permission_table` (
                                    `permission_id` VARCHAR(64) NOT NULL COMMENT '权限ID',
                                    `permission_name` VARCHAR(64) NOT NULL COMMENT '权限名称',
                                    `description` VARCHAR(255) DEFAULT NULL COMMENT '权限描述',
                                    PRIMARY KEY (`permission_id`),
                                    UNIQUE KEY `UK_permission_name` (`permission_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';


INSERT INTO `permission_table` (`permission_id`, `permission_name`, `description`) VALUES
                                                                                       ('CREATE_STUDY_ROOM', 'CREATE_STUDY_ROOM', '创建自习室'),
                                                                                       ('DELETE_STUDY_ROOM', 'DELETE_STUDY_ROOM', '删除自习室'),
                                                                                       ('VIEW_STUDY_ROOM', 'VIEW_STUDY_ROOM', '查看自习室'),
                                                                                       ('UPDATE_STUDY_ROOM', 'UPDATE_STUDY_ROOM', '更新自习室信息'),
                                                                                       ('BOOK_STUDY_ROOM', 'BOOK_STUDY_ROOM', '预订自习室'),
                                                                                       ('MANAGE_USERS', 'MANAGE_USERS', '管理用户'),
                                                                                       ('MANAGE_ROLES', 'MANAGE_ROLES', '管理角色');
