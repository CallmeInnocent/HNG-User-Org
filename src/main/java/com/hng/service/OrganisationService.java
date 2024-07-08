package com.hng.service;

import com.hng.dto.*;
import com.hng.entity.Organisation;
import com.hng.entity.User;
import com.hng.exception.OrganisationNotFoundException;
import com.hng.exception.UserNotFoundException;
import com.hng.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private UserService userService;

    public Organisation createOrganisation(User user) {
        Organisation organisation = new Organisation();
        organisation.setName(user.getFirstName() + "'s Organisation");
        organisation.setUser(user);
        return organisationRepository.save(organisation);
    }

    public OrganisationResponseDTO findByUser(User user) {
        List<Organisation> organisations = organisationRepository.findByUser(user);

        List<OrganisationDetail> organisationDetails = organisations.stream()
                .map(org -> new OrganisationDetail(org.getOrgId(), org.getName(), org.getDescription()))
                .collect(Collectors.toList());

        OrganisationData organisationData = new OrganisationData(organisationDetails);

        return new OrganisationResponseDTO("success", "Organisations fetched successfully", organisationData);
    }


    public OrganisationResponseDTO findOrganisationById(User user, String orgId) {
        Organisation organisation = organisationRepository.findByUser(user).stream()
                .filter(org -> org.getOrgId().equals(orgId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Organisation not found"));

        OrganisationDetail organisationDetail = new OrganisationDetail(organisation.getOrgId(), organisation.getName(), organisation.getDescription());
        OrganisationData organisationData = new OrganisationData(List.of(organisationDetail));

        return new OrganisationResponseDTO("success", "Organisation fetched successfully", organisationData);
    }

    public OrganisationResponseDTO createOrganisation(OrganisationCreateRequestDto request, Authentication authentication) {
        try {
            User currentUser = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Organisation organisation = new Organisation();
            organisation.setName(request.getName());
            organisation.setDescription(request.getDescription());
            organisation.setUsers(Collections.singleton(currentUser)); // Assign current user as the owner

            Organisation savedOrganisation = organisationRepository.save(organisation);

            OrganisationResponseDTO responseDTO = new OrganisationResponseDTO(
                    "success",
                    "Organisation created successfully",
                    new OrganisationData(
                            List.of(new OrganisationDetail(savedOrganisation.getOrgId(), savedOrganisation.getName(), savedOrganisation.getDescription()))
                    )
            );

            return responseDTO;
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }


    public Organisation save(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    public OrganisationResponseDTO addUserToOrganisation(String orgId, AddUserToOrganisationRequestDto request, Authentication authentication) {
        User currentUser = userService.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        Organisation organisation = organisationRepository.findById(orgId)
                .orElseThrow(OrganisationNotFoundException::new);

        User userToAdd = userService.findById(currentUser.getUserId())
                .orElseThrow(UserNotFoundException::new);

        organisation.getUsers().add(userToAdd);
        organisationRepository.save(organisation);

        return new OrganisationResponseDTO("success", "User added to organisation successfully", null);
    }
}
