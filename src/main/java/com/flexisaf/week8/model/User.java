package com.flexisaf.week7.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstname;
    @Column(name = "middle_name", nullable = false)
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastname;
    @Column(name = "contact_Address", nullable = false)
    private String contactAddress;
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}

