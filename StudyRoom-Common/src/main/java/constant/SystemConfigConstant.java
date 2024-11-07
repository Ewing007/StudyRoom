package constant;

/**
 * @Author: Ewing
 * @Date: 2024-10-08-1:43
 * @Description:
 */
public class SystemConfigConstant {

    private SystemConfigConstant() {
        throw new IllegalStateException(CONST_INSTANCE_EXCEPTION_MSG);
    }

    public static final String IP_ADDRESS_UNKNOWN = "IP地址未知";
    public static final String STUDY_ROOM_FRONT_KEY = "front";
    public static final String CONST_INSTANCE_EXCEPTION_MSG = "Constant class";

    public static final String HTTP_AUTH_HEADER_NAME = "Authorization";

    public static final String USER_ACCOUNT_BANNED = "1";
    public static final String USER_ACCOUNT_UNLOCK = "0";

    public static final String USER_ACCOUNT_DELFLAG = "1";

    public static final String USER_ACCOUNT_UNDELFLAG = "0";

    public static final String MESSAGE_STATUS_DELETED = "0";
    public static final String MESSAGE_STATUS_UNDELETED = "1";

    public static final String MESSAGE_UNDELETED_FLAG = "0";

    public static final String MESSAGE_DELETED_FLAG = "1";
    public static final String LOSTFOUND_STATUS_UNDISABLE = "1";

    public static final String LOSTFOUND_STATUS_DISABLE = "0";

    public static final String LOSTFOUND_ILLEGAL = "1";

    public static final String LOSTFOUND_UNILLEGAL = "0";

    public static final String LOSTFOUND_DELETE = "1";

    public static final String LOSTFOUND_UNDELETE = "0";

    public static final String ANNOUNCEMENT_STATUS_PUBLISHED = "0";

    public static final String ANNOUNCEMENT_STATUS_OVERDUE = "1";

    public static final String FILE_UPLOAD_DIRECTORY = "/images/";
}
