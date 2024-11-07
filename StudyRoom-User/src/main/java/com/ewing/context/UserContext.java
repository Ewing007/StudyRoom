package com.ewing.context;

/**
 * @Author: Ewing
 * @Date: 2024-10-15-22:41
 * @Description:
 */
public class UserContext {
    private String userId;
    private String username;
    private String role;

    // 构造器
    public UserContext() {}

    public UserContext(String userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}