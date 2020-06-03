package com.test.test.dto;

import jdk.jfr.Category;
import org.springframework.stereotype.Component;

@Component
public class UserDto {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}