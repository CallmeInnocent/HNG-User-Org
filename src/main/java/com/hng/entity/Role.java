package com.hng.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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
    private List<Permission> permissions = new ArrayList<>();




    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
