/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `message_table` */

DROP TABLE IF EXISTS `notifications_table`;

CREATE TABLE `notifications_table` (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT  '自增id',
                               notification_id VARCHAR(64) NOT NULL COMMENT '通知ID',
                               title VARCHAR(255) NOT NULL COMMENT '通知标题',
                               content TEXT NOT NULL COMMENT '通知内容',
                               recipient_id VARCHAR(64) NOT NULL COMMENT '接收者ID',
                               status CHAR(2) NOT NULL DEFAULT '0' COMMENT '通知状态,0-未读,1-已读',
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               type ENUM('system', 'reply', 'check') DEFAULT 'system' COMMENT '通知类型,system-系统通知,check-签到通知', --
                               is_deleted CHAR(2) NOT NULL DEFAULT '0' COMMENT '是否删除,0-未删除,1-已删除',
                               priority ENUM('low', 'medium', 'high') DEFAULT 'low' COMMENT '通知优先级',
#                                data JSON NULL COMMENT '附加的动态数据',
                               FOREIGN KEY (recipient_id) REFERENCES user_table(user_id) ON DELETE CASCADE -- 外键引用用户表
);
