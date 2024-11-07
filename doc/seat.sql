/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `seat_table` */

DROP TABLE IF EXISTS `seat_table`;

# CREATE TABLE seat_table (
#                       `id` BIGINT(20) AUTO_INCREMENT COMMENT '自增主键',
#                       `seat_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '座位ID',
#                       `room_id` VARCHAR(64) NOT NULL COMMENT '自习室ID，外键关联room表',
#                       `seat_number` VARCHAR(64) NOT NULL COMMENT '座位编号，如“1A”、“2B”',
#                       `date` DATE NOT NULL COMMENT '座位对应的日期',
#                       `status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '座位状态，0-可用、1-已预约、2-维护中',
#                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
#                       `update_time` datetime DEFAULT NULL COMMENT '更新时间',
#                       `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
#                       `description` VARCHAR(255) DEFAULT NULL COMMENT '座位描述',
#                       FOREIGN KEY (room_id) REFERENCES room_table(room_id) ON DELETE CASCADE , -- 外键约束，关联study_room表
# #                       UNIQUE KEY uniq_room_seat (room_id, seat_number) COMMENT '组合唯一索引，确保每个自习室内座位编号唯一',
#                       UNIQUE KEY uniq_room_seat_date (room_id, seat_number, date) COMMENT '组合唯一索引，确保每个自习室内座位编号在特定日期内唯一',
#                       UNIQUE KEY(id)
# ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='座位表';

CREATE TABLE seat_table (
                            `id` BIGINT(20) AUTO_INCREMENT COMMENT '自增主键',
                            `seat_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '座位ID',
                            `room_id` VARCHAR(64) NOT NULL COMMENT '自习室ID，外键关联room表',
                            `seat_number` VARCHAR(64) NOT NULL COMMENT '座位编号，如“1A”、“2B”',
                            `status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '座位状态，0-可用、1-已预约、2-维护中',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            `description` VARCHAR(255) DEFAULT NULL COMMENT '座位描述',
                            FOREIGN KEY (room_id) REFERENCES room_table(room_id) ON DELETE CASCADE,
                            UNIQUE KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='座位表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;