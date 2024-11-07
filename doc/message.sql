/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

/*Table structure for table `message_table` */

DROP TABLE IF EXISTS `message_table`;

# CREATE TABLE message_table (
#                                     `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
#                                     `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID，外键关联user表',
#                                     `content` TEXT NOT NULL COMMENT '留言的正文内容',
#                                     `reply_to` BIGINT(20) DEFAULT NULL COMMENT '回复的留言 ID，NULL 表示不是回复',
#                                     `status` CHAR(2) DEFAULT 1 COMMENT '留言状态，1 表示正常，0 表示删除',
# #                                     `del_flag` CHAR(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
#                                     `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '留言创建时间',
#                                     `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '留言更新时间',
#                                     FOREIGN KEY (user_id) REFERENCES user_table(user_id),
#                                     FOREIGN KEY (`reply_to`) REFERENCES `message_table`(`id`) ON DELETE CASCADE,
#                                     INDEX(`reply_to`)
# ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4  COMMENT '留言表';

CREATE TABLE message_table (
                               `id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
                               `message_id` VARCHAR(64) NOT NULL COMMENT '留言ID',
                               `top_message_id` VARCHAR(64) NULL DEFAULT 0 COMMENT '顶级留言ID',
                               `user_id` VARCHAR(64) NOT NULL COMMENT '用户ID，外键关联user表',
                               `content` TEXT NOT NULL COMMENT '留言的正文内容',
                               `reply_to` VARCHAR(64) DEFAULT NULL COMMENT '回复的留言 ID，NULL 表示不是回复',
                               `reply_to_user_id` VARCHAR(64) DEFAULT NULL COMMENT '回复的目标用户 ID',
                               `reply_to_user_name` VARCHAR(255) DEFAULT NULL COMMENT '回复的目标用户名称',
                               `user_avatar` VARCHAR(255) DEFAULT NULL COMMENT '回复的目标用户头像 URL',
                               `status` CHAR(2) DEFAULT '1' COMMENT '留言状态，1 表示正常，0 表示删除',
                               `del_flag` CHAR(2) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                               `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '留言创建时间',
                               `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '留言更新时间',
                               FOREIGN KEY (user_id) REFERENCES user_table(user_id),
                               FOREIGN KEY (`reply_to`) REFERENCES `message_table`(`message_id`) ON DELETE CASCADE,
                               INDEX idx_reply_to (`reply_to`),
                               INDEX idx_reply_to_user_id (`reply_to_user_id`),
                               INDEX idx_top_message_id (`top_message_id`),
                               INDEX idx_message_id (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT '留言表';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;