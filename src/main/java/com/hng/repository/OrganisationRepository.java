package com.hng.repository;

import com.hng.entity.Organisation;
import com.hng.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, String> {
    List<Organisation> findByUser(User user);
}
