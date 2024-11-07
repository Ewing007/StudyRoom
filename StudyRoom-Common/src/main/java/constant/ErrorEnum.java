package constant;

import org.aspectj.weaver.ast.Not;

/**
 * @Author: Ewing
 * @Date: 2024-09-29-23:04
 * @Description:
 */
public enum ErrorEnum {

    USER_NOT_EXIT("A400","用户账号不存在"),
    USERNOT_LOGIN("A4001","用户未登录"),
    USER_PASSWORD_ERROR("A401","用户账号或密码错误"),
    USER_VERITY_CODE_ERROR("A402", "验证码错误"),
    USER_PHONE_EXIT("A403","手机号已存在"),
    USER_EMAIL_EXIT("A4031","邮箱已存在"),
    USER_LOGIN_EXPIRED("A404", "用户登录已过期"),
    IP_ADDRESS_ERROR("A405","获取IP地址异常"),

    USER_LOGOUT("A406", "退出成功"),
    USER_ACCOUNT_BANNED("A406", "封禁成功"),
    USER_ACCOUNT_UNLOCK("A407", "解封成功"),
    USER_ACCOUNT_DELFLAG("A408", "用户账号逻辑删除成功"),
    USER_ACCOUNT_UNDELFLAG("A409", "用户账号逻辑删除取消成功"),
    USER_NOT_PERSSIONS("A4010", "用户没有权限操作"),
    MESSAGE_POST_SUCCESS("A4010", "发布留言成功"),
    MESSAGE_POST_FAILURE("A4010", "发布留言失败"),
    MESSAGE_REPLY_SUCCESS("A4010", "回复留言成功"),
    MESSAGE_REPLY_FAILURE("A4010", "回复留言失败"),
    MESSAGE_NOT_EXIT("A4010", "回复的留言不存在或已被删除"),
    LOSTFOUND_POST_SUCCESS("A4011", "发布失物招领成功"),
    LOSTFOUND_POST_FAILURE("A4012", "发布失物招领失败"),
    LOSTFOUND_POST_UNEXIT("A4013", "查询的失物招领不存在"),
    RESERVATION_CREATE_SUCCESS("A4014","座位预约时间成功"),
    RESERVATION_CREATE_FAILURE("A4015", "座位预约时间失败"),
    ANNOUNCEMENT_POST_SUCCESS("A4014", "发布公告成功"),
    ANNOUNCEMENT_POST_FAILURE("A4015", "发布公告失败"),
    ROOM_CREATE_SUCCESS("A4016", "自习室创建成功"),
    ROOM_CREATE_FAILURE("A4017", "自习室创建失败"),
    ROOM_UPDATE_SUCCESS("A4018", "自习室更新成功"),
    ROOM_UPDATE_FAILURE("A4019", "自习室更新失败"),
    ROOM_NOT_EXIT("A4020", "自习室不存在"),
    ROOM_DELETE_SUCCESS("A4021", "自习室删除成功"),

    ROOM_DELETE_FAILURE("A4022", "自习室删除失败"),
    ROOM_NOT_EXIT_SEAT("A4023", "自习室没有关联的座位"),
    ROOM_NOT_EXIT_RELATION("A4024", "自习室没有关联的预约记录"),
    SEAT_NOT_FOUND("A4025","座位号不存在"),
    TIME_SLOT_NOT_FOUND("A4026","预约时间段不存在"),
    RESERVATION_TIME_CONFLICT("A4027","预约时间段冲突"),
    NO_RESERVATIONS_FOUND("A4028","没有预约记录"),
    RESERVATION_NOT_FOUND("A4029","预约不存在"),
    RESERVATION_ALREADY_CANCELLED("A4030","预约已取消"),
    RESERVATION_CANCEL_FAILURE("A4031","取消预约失败"),
    RESERVATION_CANCEL_SUCCESS("A4032","取消预约成功"),

    RESERVATION_QUERY_SUCCESS("A4033","查询预约成功"),

    RESERVATION_QUERY_FAILURE("A4034","查询预约失败"),
    USER_UPLOAD_FILE_ERROR("A4035", "用户上传文件异常"),
    USER_UPLOAD_FILE_TYPE_NOT_MATCH("A4036","用户上传文件类型不匹配"),
    USER_AVATAR_UPDATE_SUCCESS("A4037","用户头像更新成功"),
    USER_AVATAR_UPDATE_FAILURE("A4038","用户头像更新失败"),
    ROLE_CREATE_FAILURE("A4039","角色创建失败"),
    PERMISSION_CREATE_FAILURE("A4040","权限创建失败"),
    ROLE_CREATE_SUCCESS("A4041","角色创建成功"),
    PERMISSION_CREATE_SUCCESS("A4042","权限创建成功"),
    ROLE_NOT_EXIT("A4043","角色不存在"),

    ROLE_PERMISSIONS_UPDATE_SUCCESS("A4044","角色权限更新成功"),

    PERMISSION_NOT_EXIST("A4045","权限不存在"),
    RESERVATION_PAST_DATE("A4046","预约日期已过"),
    RESERVATION_PAST_TIME("A4047","预约时间已过"),

    RESERVATION_UPDATE_SUCCESS("A4048","预约更新成功"),
    RESERVATION_UPDATE_FAILURE("A4049","预约更新失败"),
    RESERVATION_CANCEL_TOO_LATE("A4050","预约开始前30分钟内不允许取消"),

    DELETE_MESSAGE_SUCCESS("A4051","删除留言成功"),
    DELETE_MESSAGE_FAILURE("A4052","删除留言失败"),
    USER_REQUEST_PARAM_ERROR("400", "用户参数请求错误"),

    USER_UNAUTHORIZED("401", "用户未认证"),

    SYSTEM_FORBIDDEN_ERROR("403", "服务器拒绝执行此请求"),

    NOT_FOUND_ERROR("404", "请求找到资源无效"),

    METHOD_NOT_ALLOWED("405", "请求方法被禁止"),

    UKNOWN_ERROR("500", "未知错误"),
    SUCCESS("200", "OK 请求成功"),

    PARSE_DATE_ERROR("500", "日期解析异常"),
    SYSTEM_ERROR("500", "服务器内部错误");
    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
