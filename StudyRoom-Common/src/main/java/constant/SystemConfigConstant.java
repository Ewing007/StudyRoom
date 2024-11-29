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

    public static final String DEFAULT_ROLE_ID = "STUDENT_ROLE_ID";

    public static final String REPLY_MESSAGE_TOPIC = "notifications";

    public static final String SYSTEM_MESSAGE_TOPIC = "system-notifications";

    public static final String USER_CREDIT_DEDUCTION_TOPIC = "user-credit-deduction-topic";

    public static final String CHECK_IN_TOPIC = "check-in-topic";
    public static final String MESSAGE_GROUP_ID = "studyRoom-group";

    public static final String NOTIFICATION_UNREAD = "0";

    public static final String DISABLE_USER_STATUS_NOTIFICATION = "您的账号因违规操作已被禁用，请联系管理员!" ;

    public static final String USER_BOOK_SUCCESS = "请留意签到信息，成功预约自习室座位：";

    public static final String USER_BOOK_CANCEL = "取消自习室预约成功,欢迎下次预约使用!";

    public static final String MESSAGE_ILLAGAL = "您发布违规留言已被系统删除:";

    public static final String USER_CREDIT_DEDUCTION_NOTIFICATION = "未在预约规定时间内签到，扣取您相应的信誉分，请注意及时签到!";
    public static final String LOSTFOUND_ILLEGAL_NOTIFICATION = "您发布的失物招领信息违规已被系统删除:";
}
