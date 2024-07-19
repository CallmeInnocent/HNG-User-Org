package com.hng.util;

import com.hng.entity.Role;
import com.hng.entity.User;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomRoleIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        final String ROLE_PREFIX = "ORG_";

        Role role = (Role) o;

        String roleName = role.getRoleName();

        // Validate first and last names
        if (roleName == null || roleName.isEmpty()) {
            throw new IllegalArgumentException("Role name must not be null or empty");
        }

        return ROLE_PREFIX + roleName;
    }

}
