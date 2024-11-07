package com.ewing.context;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-15-22:41
 * @Description:
 */
public class UserContext {
    private String userId;
    private String username;
    private List<String> permissions;

    // 构造器
    public UserContext() {}

    public UserContext(String userId, String username, List<String> permissions) {
        this.userId = userId;
        this.username = username;
        this.permissions = permissions;
    }

    // Getter 和 Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}