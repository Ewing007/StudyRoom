/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `user_roles`;


CREATE TABLE `user_roles` (
                              `user_id` VARCHAR(64) NOT NULL COMMENT '用户id',
                              `role_id` VARCHAR(64) NOT NULL COMMENT '角色id',
                              PRIMARY KEY (`user_id`, `role_id`),
                              FOREIGN KEY (`user_id`) REFERENCES `user_table`(`user_id`) ON DELETE CASCADE,
                              FOREIGN KEY (`role_id`) REFERENCES `role_table`(`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

ALTER TABLE `user_roles`
    DROP PRIMARY KEY,
    ADD COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `unique_user_role` (`user_id`, `role_id`);

-- 将用户分配给管理员角色
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES ('1845388123132006400', 'ADMIN_ROLE_ID'); -- 假设 user_001 是管理员用户

-- 将用户分配给学生角色
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES ('1844676429338513408', 'STUDENT_ROLE_ID'); -- 假设 user_002 是学生用户

-- 将用户分配给学生角色
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES ('1844782819595915264', 'STUDENT_ROLE_ID'); -- 假设 user_002 是学生用户
