/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `message_table` */

DROP TABLE IF EXISTS `role_permissions`;



CREATE TABLE `role_permissions` (
#     `role_id` VARCHAR(64) NOT NULL COMMENT '角色ID',
#     `permission_id` VARCHAR(64) NOT NULL COMMENT '权限ID',
#     PRIMARY KEY (`role_id`, `permission_id`),
#     FOREIGN KEY (`role_id`) REFERENCES `role_table`(`role_id`) ON DELETE CASCADE,
#     FOREIGN KEY (`permission_id`) REFERENCES `permission_table`(`permission_id`) ON DELETE CASCADE
                                    id Bigint AUTO_INCREMENT PRIMARY KEY,
                                    role_id VARCHAR(64) NOT NULL,
                                    permission_id VARCHAR(64) NOT NULL,
                                    UNIQUE KEY unique_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';


-- 分配权限给管理员角色
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES
                                                                ('admin_role_id', 'CREATE_STUDY_ROOM'),
                                                                ('admin_role_id', 'DELETE_STUDY_ROOM'),
                                                                ('admin_role_id', 'VIEW_STUDY_ROOM'),
                                                                ('admin_role_id', 'UPDATE_STUDY_ROOM'),
                                                                ('admin_role_id', 'BOOK_STUDY_ROOM'),
                                                                ('admin_role_id', 'MANAGE_USERS'),
                                                                ('admin_role_id', 'MANAGE_ROLES');

-- 分配权限给学生角色
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES
                                                                ('student_role_id', 'VIEW_STUDY_ROOM'),
                                                                ('student_role_id', 'BOOK_STUDY_ROOM');
