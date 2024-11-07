package context;

import lombok.experimental.UtilityClass;

/**
 * @Author: Ewing
 * @Date: 2024-06-24-16:34
 * @Description: 用户信息持有类
 */
@UtilityClass
public class UserInfoContextHandler {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public  void setUserContext(UserContext context) {
        userContext.set(context);
    }

    public  UserContext getUserContext() {
        return userContext.get();
    }

    public  void clear() {
        userContext.remove();
    }



}
