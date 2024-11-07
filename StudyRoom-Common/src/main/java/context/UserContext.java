package context;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-11-21:42
 * @Description:
 */

public class UserContext {
    private String userId;
    private String userName;

    private List<String> permissions;

    // 构造器
    public UserContext(String userId, String userName, List<String> permissions) {
        this.userId = userId;
        this.userName = userName;
        this.permissions = permissions;
    }

    // Getter 和 Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}

