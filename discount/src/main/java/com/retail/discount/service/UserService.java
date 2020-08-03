package com.retail.discount.service;

import com.retail.discount.dtos.UserDTO;
import com.retail.discount.dtos.UserTypeDTO;
import com.retail.discount.entity.User;
import com.retail.discount.entity.UserType;
import com.retail.discount.repository.UserRepository;
import com.retail.discount.repository.UserTypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The User service.
 */
@Log4j2
@Service
public class UserService {


    private final UserRepository repository;
    private final UserTypeRepository userTypeRepository;


    /**
     * Instantiates a new User service.
     *
     * @param repository         the repository
     * @param userTypeRepository the user type repository
     */
    public UserService(UserRepository repository, UserTypeRepository userTypeRepository) {
        this.repository = repository;
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    public UserDTO getUser(String id) {

        log.info("Find user by id {}", id);
        User user = this.findById(id);

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .startDate(user.getStartDate())
                .userTypeDTO(UserTypeDTO.builder()
                        .id(user.getUserType().getId())
                        .type(user.getUserType().getType())
                        .build())
                .build();
    }

    /**
     * Get all list.
     *
     * @return the list
     */
    public List<UserDTO> getAllUser(){

        log.info("Getting all Users");
        return this.repository.findAll().stream().map(
                user -> UserDTO.builder().startDate(user.getStartDate())
                        .phone(user.getPhone())
                        .lastName(user.getLastName())
                        .firstName(user.getFirstName())
                        .email(user.getEmail())
                        .id(user.getId())
                        .userTypeDTO(UserTypeDTO.builder()
                                .id(user.getUserType().getId())
                                .type(user.getUserType().getType())
                                .build())
                        .build()).collect(Collectors.toList());
    }

    /**
     * Create user user dto.
     *
     * @param userDTO the user entity
     * @param userTypeId the user type id
     * @return the user dto
     */
    public UserDTO createUser(UserDTO userDTO, String userTypeId){


        this.validateUserType(userTypeId);

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(userDTO.getEmail())
                .lastName(userDTO.getLastName())
                .firstName(userDTO.getFirstName())
                .phone(userDTO.getPhone())
                .startDate(userDTO.getStartDate())
                .userType(UserType.builder()
                        .id(userDTO.getUserTypeDTO().getId())
                        .type(userDTO.getUserTypeDTO().getType())
                        .build())
                .build();


        this.repository.save(user);

        return UserDTO.builder()
                    .id(user.getId())
                    .build();
    }

    /**
     * Find customer by id customer.
     *
     * @param id
     * @return
     */
    private User findById (String id){
        return  repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("USER_DOES_NOT_EXIST " + id));
    }

    /**
     * Validate User Type
     * @param userTypeId
     */
    private void validateUserType(String userTypeId) {
        if(!userTypeRepository.findById(userTypeId).isPresent()){
            throw new EntityNotFoundException("not found record user type");
        }
    }

}
