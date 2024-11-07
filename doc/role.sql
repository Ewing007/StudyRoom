/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `role_table`;

CREATE TABLE `role_table` (
                              `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID自增',
                              `role_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '角色id',
                              `role_name` VARCHAR(64) NOT NULL UNIQUE COMMENT '角色名称',
                              `description` VARCHAR(255) DEFAULT NULL COMMENT '角色描述',
                              UNIQUE KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- 插入管理员角色
INSERT INTO `role_table` (`role_id`, `role_name`, `description`)
VALUES ('ADMIN_ROLE_ID', 'ADMIN', '管理员角色，具有系统最高权限');

-- 插入学生角色
INSERT INTO `role_table` (`role_id`, `role_name`, `description`)
VALUES ('STUDENT_ROLE_ID', 'STUDENT', '学生角色，具有基本使用权限');
