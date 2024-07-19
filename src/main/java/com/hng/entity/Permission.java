package com.hng.entity;




import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String permissionId;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles = new ArrayList<>();
}

