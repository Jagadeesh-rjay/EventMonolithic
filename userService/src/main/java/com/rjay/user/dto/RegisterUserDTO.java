package com.rjay.user.dto;

import org.springframework.beans.BeanUtils;
import com.rjay.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {    
    private String firstName;
    private String lastName;
    private String emailId;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;
    private String role;
    private Long mob;
    private String street;
    private String city;
    private String state;
    private int pincode;    

    // Converts RegisterUserDTO to User entity
    public static User toUserEntity(RegisterUserDTO registerUserDto) {
        User user = new User();
        BeanUtils.copyProperties(registerUserDto, user);
        return user;
    }

    // Setter for password with validation
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
        log.info("Password is set successfully");
    }

    // Setter for confirmPassword with validation
    public void setConfirmPassword(String confirmPassword) {
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Confirm password cannot be null or empty");
        }
        this.confirmPassword = confirmPassword;
        log.info("Confirm Password is set successfully");
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getMob() {
        return mob;
    }

    public void setMob(Long mob) {
        this.mob = mob;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

	
}
