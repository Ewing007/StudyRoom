USE `studyroom`;

/*Table structure for table `seat_table` */

DROP TABLE IF EXISTS `seat_time_table`;


CREATE TABLE seat_time_table (
                                 id BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
                                 room_id VARCHAR(64) NOT NULL COMMENT '房间ID，关联 room_table',
                                 seat_id VARCHAR(64) NOT NULL COMMENT '座位ID，关联 seat_table',
                                 seat_number VARCHAR(64) NOT NULL COMMENT '座位号',
                                 slot_id VARCHAR(64) NOT NULL COMMENT '时间段ID，关联 time_slot_table',
                                 status CHAR(2) NOT NULL DEFAULT '0' COMMENT '状态，0-可用，1-已预约，2-维护中,3-已过期',
                                 date DATE NOT NULL COMMENT '预约日期',
                                 FOREIGN KEY (seat_id) REFERENCES seat_table(seat_id) ON DELETE CASCADE,
                                 FOREIGN KEY (slot_id) REFERENCES time_slot_table(slot_id) ON DELETE CASCADE,
                                 UNIQUE KEY uniq_seat_slot_date (seat_id, slot_id, date) COMMENT '唯一约束，确保座位、时间段和日期唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='座位时间段表';
