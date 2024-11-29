/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
# CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `reservation_table` */

DROP TABLE IF EXISTS `check_in_table`;

CREATE TABLE check_in_table (
                                `id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '签到ID，自增主键',
                                `qr_code_id` VARCHAR(64) NOT NULL COMMENT '二维码ID',
                                `reservation_id` VARCHAR(64) NOT NULL COMMENT '预约号，关联reservation_table表',
                                `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID，关联user_table表',
                                `check_in_time` DATETIME NULL COMMENT '签到时间',
#                                 `check_out_time` DATETIME DEFAULT NULL COMMENT '签退时间',
                                `check_in_qr_code` TEXT NOT NULL COMMENT '签到二维码内容',
                                `status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '签到状态 0-未签到,1-已签到,2-已过期',
                                `expire_time` DATETIME DEFAULT NULL COMMENT '二维码失效时间',
                                `notes` VARCHAR(64) DEFAULT NULL COMMENT '备注信息',
                                CONSTRAINT fk_check_in_reservation FOREIGN KEY (reservation_id) REFERENCES reservation_table(reservation_id) ON DELETE CASCADE,
                                CONSTRAINT fk_check_in_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE,
                                INDEX idx_reservation_id (reservation_id) COMMENT '按预约号查询',
                                INDEX idx_user_id (user_id) COMMENT '按用户查询',
                                INDEX idx_check_in_time (check_in_time) COMMENT '按签到时间查询',
#                                 INDEX idx_check_out_time (check_out_time) COMMENT '按签退时间查询',
                                INDEX idx_status (status) COMMENT '按状态查询'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='自习室签到表';
