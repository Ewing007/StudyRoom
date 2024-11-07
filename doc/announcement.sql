/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studyroom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `studyroom`;

DROP TABLE IF EXISTS `announcement_table`;

CREATE TABLE announcement_table (
                                    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '公告ID，自增主键',  -- 公告ID，自增主键
                                    `title` VARCHAR(64) NOT NULL COMMENT '公告标题',           -- 公告标题
                                    `content` TEXT NOT NULL COMMENT '公告内容',                 -- 公告内容
                                    `author_id` VARCHAR(64) NOT NULL COMMENT '发布者ID，关联管理员',             -- 发布者ID，关联管理员
                                    `author_name` VARCHAR(50) NOT NULL COMMENT '发布者用户名',      -- 发布者用户名
                                    `created_time` datetime DEFAULT NULL COMMENT '公告发布时间',  -- 公告发布时间
                                    `status` CHAR(2) NOT NULL DEFAULT '0' COMMENT '公告状态，0为已发布，1为已过期' ,                   -- 公告状态，1为已发布，0为草稿
                                    `start_time` datetime DEFAULT NULL COMMENT '公告开始时间',                  -- 公告开始时间
                                    `end_time` datetime DEFAULT NULL COMMENT '公告结束时间' ,                    -- 公告结束时间
                                    `update_time` datetime DEFAULT NULL COMMENT '公告更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='公告表';



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


INSERT INTO announcement_table
(title, content, author_id, author_name, created_time, status, start_time, end_time)
VALUES
    ('有效公告 1', '这是一个有效的公告，结束时间在未来。', 'admin123', '管理员', NOW(), '0', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY)),
    ('有效公告 2', '另一个有效的公告，结束时间在未来。', 'admin123', '管理员', NOW(), '0', NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY)),
    ('有效公告 3', '更多有效的公告，结束时间在未来。', 'admin123', '管理员', NOW(), '0', NOW(), DATE_ADD(NOW(), INTERVAL 3 DAY));
INSERT INTO announcement_table
(title, content, author_id, author_name, created_time, status, start_time, end_time)
VALUES
    ('已过期公告 1', '这是一个已过期的公告，结束时间在过去。', 'admin123', '管理员', NOW(), '0', NOW(), DATE_SUB(NOW(), INTERVAL 1 DAY)),
    ('已过期公告 2', '另一个已过期的公告，结束时间在过去。', 'admin123', '管理员', NOW(), '0', NOW(), DATE_SUB(NOW(), INTERVAL 2 DAY));
INSERT INTO announcement_table
(title, content, author_id, author_name, created_time, status, start_time, end_time)
VALUES
    ('已过期公告 3', '这是一个已过期的公告，状态已设置为过期。', 'admin123', '管理员', NOW(), '1', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
    ('已过期公告 4', '另一个已过期的公告，状态已设置为过期。', 'admin123', '管理员', NOW(), '1', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY));
