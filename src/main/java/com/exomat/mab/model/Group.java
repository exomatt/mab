package com.exomat.mab.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "group_table")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @NotBlank(message = "Name is mandatory")
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userGroup")
    private List<User> users;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
