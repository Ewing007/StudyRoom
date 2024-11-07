USE `studyroom`;

/*Table structure for table `seat_table` */

DROP TABLE IF EXISTS `time_slot_table`;

CREATE TABLE time_slot_table (
    `id` BIGINT(20) AUTO_INCREMENT COMMENT '自增主键',
    `slot_id` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '时间槽ID',
    `start_time` TIME NOT NULL COMMENT '时间段开始时间',
    `end_time` TIME NOT NULL COMMENT '时间段结束时间',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `del_flag` CHAR(2) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    UNIQUE KEY uniq_start_end (start_time, end_time) COMMENT '组合唯一索引，确保时间段唯一',
    UNIQUE KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='时间槽表';

INSERT INTO time_slot_table (slot_id, start_time, end_time, create_time, update_time, del_flag) VALUES
('slot_0000_0100', '00:00:00', '01:00:00', NOW(), NOW(), '0'),
('slot_0100_0200', '01:00:00', '02:00:00', NOW(), NOW(), '0'),
('slot_0200_0300', '02:00:00', '03:00:00', NOW(), NOW(), '0'),
('slot_0300_0400', '03:00:00', '04:00:00', NOW(), NOW(), '0'),
('slot_0400_0500', '04:00:00', '05:00:00', NOW(), NOW(), '0'),
('slot_0500_0600', '05:00:00', '06:00:00', NOW(), NOW(), '0'),
('slot_0600_0700', '06:00:00', '07:00:00', NOW(), NOW(), '0'),
('slot_0700_0800', '07:00:00', '08:00:00', NOW(), NOW(), '0'),
('slot_0800_0900', '08:00:00', '09:00:00', NOW(), NOW(), '0'),
('slot_0900_1000', '09:00:00', '10:00:00', NOW(), NOW(), '0'),
('slot_1000_1100', '10:00:00', '11:00:00', NOW(), NOW(), '0'),
('slot_1100_1200', '11:00:00', '12:00:00', NOW(), NOW(), '0'),
('slot_1200_1300', '12:00:00', '13:00:00', NOW(), NOW(), '0'),
('slot_1300_1400', '13:00:00', '14:00:00', NOW(), NOW(), '0'),
('slot_1400_1500', '14:00:00', '15:00:00', NOW(), NOW(), '0'),
('slot_1500_1600', '15:00:00', '16:00:00', NOW(), NOW(), '0'),
('slot_1600_1700', '16:00:00', '17:00:00', NOW(), NOW(), '0'),
('slot_1700_1800', '17:00:00', '18:00:00', NOW(), NOW(), '0'),
('slot_1800_1900', '18:00:00', '19:00:00', NOW(), NOW(), '0'),
('slot_1900_2000', '19:00:00', '20:00:00', NOW(), NOW(), '0'),
('slot_2000_2100', '20:00:00', '21:00:00', NOW(), NOW(), '0'),
('slot_2100_2200', '21:00:00', '22:00:00', NOW(), NOW(), '0'),
('slot_2200_2300', '22:00:00', '23:00:00', NOW(), NOW(), '0'),
('slot_2300_2400', '23:00:00', '24:00:00', NOW(), NOW(), '0');

