package constant;

import lombok.Getter;

/**
 * @Author: Ewing
 * @Date: 2024-10-10-12:07
 * @Description:
 */
public class DataBaseConstant {

    /**
     * @description: 用户信息表
     * @author ewing
     * @date 2024/6/18 20:47
     * @version 1.0
     */
    public static class UserInfoTable {
        private UserInfoTable() {
            throw new IllegalStateException(SystemConfigConstant.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_USERNAME = "user_name";

        public static final String COLUMN_USERNAME_PHONE = "phone_number";

        public static final String COLUMN_USERID = "user_id";

        public static final String COLUMN_USER_EMAIL = "email";

        public static final Integer DEFAULT_STATUS_VALUE = 0;

        public static final String DEFAULT_SALT_VALUE = "0";
    }

    public static class LostFoundTable {
        private LostFoundTable() {
            throw new IllegalStateException(SystemConfigConstant.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_USERID = "user_id";

        public static final String COLUMN_ITEM_TYPE = "item_type";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_STATUS = "status";

        public static final String COLUMN_LOCATION = "location";
    }


    public static class RoomTable {
        private RoomTable() {
            throw new IllegalStateException(SystemConfigConstant.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String ROOM_ID = "room_id";


    }

    public static class RESVERATIONTABLE {
        private RESVERATIONTABLE() {
            throw new IllegalStateException(SystemConfigConstant.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String SEAT_ID = "seat_id";


    }

    /**
     * @description: sql语句枚举类
     * @param :  :
     * @return
     * @author: ewing
     * @date: 2024/6/18 21:25
     */
    @Getter
    public enum sqlEnum {
        /**
         * @description: SQL语句
         * @author ewing
         * @date 2024/6/18 21:26
         * @version 1.0
         */
        LIMIT_1("limit 1"),
        LIMIT_2("limit 2"),
        LIMIT_5("limit 5"),
        LIMIT_30("limit 30"),
        LIMIT_100("limit 100");

        private String sql;

        sqlEnum(String sql) {
            this.sql = sql;
        }
    }
}
