package com.ewing.Enum;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Ewing
 * @Date: 2024-11-03-19:51
 * @Description:
 */
@Slf4j
public enum TimeSlot {
    SLOT_0000_0100("00:00:00", "01:00:00"),
    SLOT_0100_0200("01:00:00", "02:00:00"),
    SLOT_0200_0300("02:00:00", "03:00:00"),
    SLOT_0300_0400("03:00:00", "04:00:00"),
    SLOT_0400_0500("04:00:00", "05:00:00"),
    SLOT_0500_0600("05:00:00", "06:00:00"),
    SLOT_0600_0700("06:00:00", "07:00:00"),
    SLOT_0700_0800("07:00:00", "08:00:00"),
    SLOT_0800_0900("08:00:00", "09:00:00"),
    SLOT_0900_1000("09:00:00", "10:00:00"),
    SLOT_1000_1100("10:00:00", "11:00:00"),
    SLOT_1100_1200("11:00:00", "12:00:00"),
    SLOT_1200_1300("12:00:00", "13:00:00"),
    SLOT_1300_1400("13:00:00", "14:00:00"),
    SLOT_1400_1500("14:00:00", "15:00:00"),
    SLOT_1500_1600("15:00:00", "16:00:00"),
    SLOT_1600_1700("16:00:00", "17:00:00"),
    SLOT_1700_1800("17:00:00", "18:00:00"),
    SLOT_1800_1900("18:00:00", "19:00:00"),
    SLOT_1900_2000("19:00:00", "20:00:00"),
    SLOT_2000_2100("20:00:00", "21:00:00"),
    SLOT_2100_2200("21:00:00", "22:00:00"),
    SLOT_2200_2300("22:00:00", "23:00:00"),
    SLOT_2300_2400("23:00:00", "24:00:00");

    private final String startTime;
    private final String endTime;

    TimeSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public static TimeSlot fromSlotId(String slotId) {
        for (TimeSlot timeSlot : values()) {
            if (timeSlot.name().equalsIgnoreCase(slotId)) {
                return timeSlot;
            }
        }
        throw new IllegalArgumentException("Invalid slotId: " + slotId);
    }

    public static String getSlotNameByTimeRange(String timeRange) {
        for (TimeSlot timeSlot : TimeSlot.values()) {
            String expectedTimeRange = timeSlot.getStartTime() + " - " + timeSlot.getEndTime();
            if (expectedTimeRange.equals(timeRange)) {
                return timeSlot.name();
            }
        }
        throw new IllegalArgumentException("无效的时间范围: " + timeRange);
    }


    public static TimeSlot getTimeRangeBySlotId(String slotId) {
        for (TimeSlot timeSlot : values()) {
            if (timeSlot.name().equalsIgnoreCase(slotId)) {
                return timeSlot;
            }
        }
        throw new IllegalArgumentException("Invalid slotId: " + slotId);
    }

}
