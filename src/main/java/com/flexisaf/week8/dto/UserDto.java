package com.flexisaf.week8.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "please input your firstName")
    private String firstName;

    @NotEmpty(message = "please input your middleName")
    private String middleName;

    @NotEmpty(message = "please input your lastName")
    private String lastName;

    @NotEmpty(message = "kindly input your address")
    private String contactAddress;

    @NotEmpty(message = "kindly input your phoneNumber")
    private String phoneNumber;

    @NotEmpty(message = "Please provide a valid email address")
    @Email
    private String email;

    @NotEmpty(message = "Please provide your password")
    private String password;

    @Column(name = "is_logged_in", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isLoggedIn;

}
