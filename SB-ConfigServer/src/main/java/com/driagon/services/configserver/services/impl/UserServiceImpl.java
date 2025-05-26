package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.mappers.UserMapper;
import com.driagon.services.configserver.repositories.IUserRepository;
import com.driagon.services.configserver.services.IUserService;
import com.driagon.services.error.exceptions.BusinessException;
import com.driagon.services.logging.annotations.ExceptionLog;
import com.driagon.services.logging.annotations.Loggable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.event.Level;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;

    private final UserMapper mapper;

    private final StringEncryptor encryptor;

    /**
     * Retrieves all users in the system.
     *
     * @return a set of all users
     */
    @Override
    public Set<UserResponse> getAllUsers() {
        return Set.of();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user with the specified ID, or null if not found
     */
    @Override
    public UserResponse getUserById(Long userId) {
        return null;
    }

    /**
     * Creates a new user with the provided details.
     *
     * @param userRequest the details of the user to create
     * @return the created user
     */
    @Override
    @Transactional
    @Loggable(
            level = Level.INFO,
            exceptions = {
                    @ExceptionLog(
                            value = BusinessException.class,
                            message = "The email provided is already in use."
                    ),
                    @ExceptionLog(
                            value = DataAccessException.class,
                            message = "An exception occurred while accessing the database.",
                            printStackTrace = true
                    )
            },
            exceptionLevel = Level.ERROR
    )
    public UserResponse createUser(UserRequest userRequest) {
        this.repository.findByEmail(userRequest.getEmail())
                .ifPresent(user -> {
                    throw new BusinessException("This email is already in use.");
                });

        // User does not exist, proceed with creation
        var user = this.mapper.mapRequestToEntity(userRequest);
        log.info("Encrypting password for user {}", user.getEmail());
        user.setPassword(this.encryptor.encrypt(user.getPassword()));

        try {
            user = this.repository.save(user);
            log.info("User created successfully with ID {}", user.getId());
            return this.mapper.mapEntityToResponse(user);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing user with the provided details.
     *
     * @param userId      the ID of the user to update
     * @param userRequest the new details for the user
     * @return the updated user
     */
    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        return null;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    @Override
    public boolean deleteUser(Long userId) {
        return false;
    }
}