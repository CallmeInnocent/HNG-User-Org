package com.hng.util;

import com.hng.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomUserIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        User user = (User) object;

        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        // Validate first and last names
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("First name and last name must not be null or empty");
        }

        // Generate userId based on the first character of firstName and lastName
        String firstCharOfFirstName = firstName.substring(0, 1).toLowerCase();

        return firstCharOfFirstName + lastName.toLowerCase();
    }
}
