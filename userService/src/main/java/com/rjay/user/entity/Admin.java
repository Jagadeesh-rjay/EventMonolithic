package com.rjay.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email should not exceed 100 characters")
    private String emailId;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;

    @NotBlank(message = "Role is mandatory")
    @Size(max = 50, message = "Role should not exceed 50 characters")
    private String role;

    @NotBlank(message = "Status is mandatory")
    @Pattern(regexp = "^(active|inactive)$", message = "Status must be either 'active' or 'inactive'")
    private String status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override equals and hashCode for entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                Objects.equals(emailId, admin.emailId) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(role, admin.role) &&
                Objects.equals(status, admin.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailId, password, role, status);
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", emailId='" + emailId + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
