package com.exomat.mab.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity(name = "user_table")
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NonNull
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NonNull
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NonNull
    @NotBlank(message = "Email is mandatory")
    private String email;
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group userGroup;
}
