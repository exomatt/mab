package com.exomat.mab.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "user_table")
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group userGroup;
}
