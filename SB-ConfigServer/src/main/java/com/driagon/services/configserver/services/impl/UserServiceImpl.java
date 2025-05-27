package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.dto.requests.UpdateUserRequest;
import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.mappers.UserMapper;
import com.driagon.services.configserver.repositories.IRoleRepository;
import com.driagon.services.configserver.repositories.IUserRepository;
import com.driagon.services.configserver.services.IUserService;
import com.driagon.services.error.exceptions.BusinessException;
import com.driagon.services.error.exceptions.NotFoundException;
import com.driagon.services.error.exceptions.ProcessException;
import com.driagon.services.logging.annotations.ExceptionLog;
import com.driagon.services.logging.annotations.Loggable;
import com.driagon.services.logging.constants.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;

    private final IRoleRepository roleRepository;

    private final UserMapper mapper;

    private final StringEncryptor encryptor;

    @Override
    @Loggable(message = "Retrieving all users", level = Level.INFO, exceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "No users found in the system."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional(readOnly = true)
    public Set<UserResponse> getAllUsers() {
        try {
            var users = repository.findAll()
                    .stream()
                    .map(mapper::mapUserEntityToUserResponse)
                    .collect(Collectors.toSet());

            if (users.isEmpty()) {
                throw new NotFoundException("No users found in the system.");
            }

            return users;
        } catch (DataAccessException e) {
            log.error("Database access error.", e);
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }

    @Override
    @Loggable(message = "Retrieving user by Email", level = Level.INFO, exceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "User with ID {0} not found."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        var user = this.repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found."));
        return this.mapper.mapUserEntityToUserResponse(user);
    }

    @Override
    @Loggable(message = "Creating user", level = Level.INFO, exceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = BusinessException.class,
                    message = "The email provided is already in use."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An exception occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        this.repository.findByEmail(userRequest.getEmail())
                .ifPresent(user -> {
                    throw new BusinessException("This email is already in use.");
                });

        // User does not exist, proceed with creation
        var user = this.mapper.mapUserRequestToUserEntity(userRequest);
        log.info("Encrypting password for user {}", user.getEmail());
        user.setPassword(this.encryptor.encrypt(user.getPassword()));

        try {
            user = this.repository.save(user);
            log.info("User created successfully with ID {}", user.getId());
            return this.mapper.mapUserEntityToUserResponse(user);
        } catch (DataAccessException e) {
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }

    @Override
    @Loggable(message = "Updating user with email {0}", level = Level.INFO, exceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "User with email {0} not found."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional
    public UserResponse updateUser(String email, UpdateUserRequest request) {
        try {
            var user = this.repository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User with email " + email + " not found."));

            var role = this.roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new NotFoundException("Role with ID " + request.getRoleId() + " not found."));

            this.mapper.mapUpdateUserRequestToUserEntity(request, user);
            user.setRole(role);

            user = this.repository.save(user);
            return this.mapper.mapUserEntityToUserResponse(user);
        } catch (DataAccessException e) {
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }

    @Override
    public boolean deleteUser(Long userId) {
        return false;
    }
}