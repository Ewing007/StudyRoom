package constant;

/**
 * @Author: Ewing
 * @Date: 2024-10-08-10:56
 * @Description:
 */
public class ApiRouterConstant {

    public final static String API_BASE_URL_PREFIX = "/api";

    public final static String API_FRONT_URL_PREFIX = API_BASE_URL_PREFIX + "/front";

//    public final static String STUDENT_URL_PREFIX = API_FRONT_URL_PREFIX + "/student";
//
//    public final static String TEACHER_URL_PREFIX = API_FRONT_URL_PREFIX + "/teacher";
//
//    public final static String ADMIN_URL_PREFIX = API_FRONT_URL_PREFIX + "/admin";
    public final static String USER_URL_PREFIX = API_FRONT_URL_PREFIX + "/user";

    public final static String ADMIN_URL_PREFIX = API_FRONT_URL_PREFIX + "/admin";
    public final static String IMAGE_CODE_URL_PREFIX = API_FRONT_URL_PREFIX + "/image";
    public static final String LOG_URL_PREFIX = API_FRONT_URL_PREFIX + "/log";

    public static final String ROOM_URL_PREFIX = API_FRONT_URL_PREFIX + "/room";

    public static final String TIME_URL_PREFIX = API_FRONT_URL_PREFIX + "/time";

    public static final String FILE_UPLOAD_URL_PREFIX = API_FRONT_URL_PREFIX + "/file/upload";
    public static final String ROOM_RESERVATION_URL_PREFIX = ROOM_URL_PREFIX + "/reservation";
    public static final String ROOM_SEAT_URL_PREFIX = ROOM_URL_PREFIX + "/seat";
    public static final String MESSAGE_URL_PREFIX = API_FRONT_URL_PREFIX + "/message";
    public static final String LOSS_FOUND_URL_PREFIX = API_FRONT_URL_PREFIX + "/found";
    public static final String ANNOUNCEMENT_FOUND_URL_PREFIX = API_FRONT_URL_PREFIX + "/announcement";
}
