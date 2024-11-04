package com.flexisaf.week8.dto;

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
    private Long id;

    @NotEmpty(message = "please input your firstName")
    private String firstName;

    @NotEmpty (message = "please input your middleName")
    private String middleName;

    @NotEmpty (message = "please input your lastName")
    private String lastName;

    @NotEmpty (message = "kindly input your address")
    private String contactAddress;

    @NotEmpty (message = "kindly input your phoneNuumber")
    private String phoneNumber;

    @NotEmpty (message = "Please provide a valid email address")
    @Email
    private String email;
    @NotEmpty (message = "Please provide your password")
    private String password;

}