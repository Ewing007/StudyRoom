/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
# CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `reservation_table` */

DROP TABLE IF EXISTS `reservation_table`;

CREATE TABLE reservation_table (
                             `id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '预约ID，自增主键',
                             `reservation_id` VARCHAR(64) NOT NULL COMMENT '预约号，唯一id',
                             `room_id` VARCHAR(64) NOT NULL COMMENT '自习室ID，外键关联room表',                                 --
                             `slot_id` VARCHAR(64) NOT NULL COMMENT '时间槽ID，外键关联time_slot表',
                             `seat_id` VARCHAR(64) NOT NULL COMMENT '座位ID，外键关联seat表',                                     --
                             `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID，外键关联user表',
                             `date` DATE NOT NULL COMMENT '预约日期',
                             `start_time` DATETIME NOT NULL COMMENT '预约开始时间',                                -- 预约开始时间
                             `end_time` DATETIME NOT NULL COMMENT '预约结束时间',                                  --
                             `reservation_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预约创建时间', --
                             `cancel_time` DATETIME DEFAULT NULL COMMENT '预约取消时间',
                             `finish_time` DATETIME DEFAULT NULL COMMENT '预约完成时间',
                             `penalty_time` DATETIME DEFAULT NULL COMMENT '违约时间',
                             `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
                             `cancel_pay_time` DATETIME DEFAULT NULL COMMENT '取消或支付时间',
                             `status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '预约状态 0-已确认,1-已取消,2-完成,3-违约',
                             `payment_status` CHAR(2) DEFAULT 0 COMMENT '支付状态 0-未支付 1-已支付',
                             `check_in_status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '签到状态（0-未签到，1-已签到）',
                             `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                             `notes` VARCHAR(64) DEFAULT NULL COMMENT '备注信息',                                                   --
                             CONSTRAINT fk_reservation_slot_time FOREIGN KEY (slot_id) REFERENCES time_slot_table(slot_id) ON DELETE CASCADE,
                             CONSTRAINT fk_reservation_seat FOREIGN KEY (seat_id) REFERENCES seat_table(seat_id) ON DELETE CASCADE,
                             CONSTRAINT fk_reservation_room FOREIGN KEY (room_id) REFERENCES room_table(room_id) ON DELETE CASCADE,
                             CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE,
                             CONSTRAINT chk_end_after_start CHECK (end_time > start_time),
                             CONSTRAINT chk_status_valid CHECK (status IN ('0', '1', '2', '3')),
                             INDEX idx_user_id (user_id) COMMENT '按用户查询',
                             INDEX idx_seat_id (seat_id) COMMENT '按座位查询',
                             INDEX idx_start_time (start_time) COMMENT '按开始时间查询',
                             INDEX idx_end_time (end_time) COMMENT '按结束时间查询',
                             INDEX idx_status (status) COMMENT '按状态查询',
                             INDEX idx_date (date) COMMENT '按日期查询',
                             INDEX idx_slot_id (slot_id) COMMENT '按时间槽查询'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='自习室座位预约表';



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;