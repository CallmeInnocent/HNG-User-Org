package com.hng.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(generator = "customRoleIdGenerator")
    @GenericGenerator(name = "customRoleIdGenerator", strategy = "com.hng.util.CustomRoleIdGenerator")
    private String roleId;

    @Column(unique = true)
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organisation organisation;

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;


    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
