package com.HNG_Organisation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrgId;


    @Column
    @NotNull
    @NotEmpty(message = "Required: Cannot be empty")
    private String name;

    @Column
    @NotNull
    @NotEmpty(message = "Required: Cannot be empty")
    private String description;

    @ManyToMany(mappedBy = "organisations")
    private List<User> users;

}
