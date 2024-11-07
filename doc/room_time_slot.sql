USE `studyroom`;

/*Table structure for table `seat_table` */

DROP TABLE IF EXISTS `room_time_slot_table`;


CREATE TABLE room_time_slot_table (
                                      id BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
                                      room_id VARCHAR(64) NOT NULL COMMENT '自习室ID，关联 room_table',
                                      slot_id VARCHAR(64) NOT NULL COMMENT '时间段ID，关联 time_slot_table',
                                      FOREIGN KEY (room_id) REFERENCES room_table(room_id) ON DELETE CASCADE,
                                      FOREIGN KEY (slot_id) REFERENCES time_slot_table(slot_id) ON DELETE CASCADE,
                                      UNIQUE KEY uniq_room_slot (room_id, slot_id) COMMENT '唯一约束，确保自习室与时间段唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自习室开放时间段表';
