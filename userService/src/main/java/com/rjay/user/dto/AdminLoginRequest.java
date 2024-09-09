package com.rjay.user.dto;
import lombok.Data;


@Data
public class AdminLoginRequest {
    private String emailId;
    private String password;

    // Default constructor
    public AdminLoginRequest() {}

    // Constructor with parameters
    public AdminLoginRequest(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    // Getters and setters
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
