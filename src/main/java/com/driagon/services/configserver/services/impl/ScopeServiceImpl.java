package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.dto.requests.CreateScopeRequest;
import com.driagon.services.configserver.dto.requests.SetPropertyRequest;
import com.driagon.services.configserver.dto.responses.CreateScopeResponse;
import com.driagon.services.configserver.dto.responses.ScopeResponse;
import com.driagon.services.configserver.entities.Property;
import com.driagon.services.configserver.entities.Scope;
import com.driagon.services.configserver.entities.User;
import com.driagon.services.configserver.mappers.ScopeMapper;
import com.driagon.services.configserver.repositories.IScopeRepository;
import com.driagon.services.configserver.repositories.IUserRepository;
import com.driagon.services.configserver.services.IScopeService;
import com.driagon.services.error.exceptions.BusinessException;
import com.driagon.services.error.exceptions.NotFoundException;
import com.driagon.services.error.exceptions.ProcessException;
import com.driagon.services.logging.annotations.Loggable;
import com.driagon.services.logging.utils.MaskedLogger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScopeServiceImpl implements IScopeService {

    private final IScopeRepository scopeRepository;

    private final ScopeMapper mapper;

    private final IUserRepository userRepository;

    private static final MaskedLogger log = MaskedLogger.getLogger(ScopeServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    @Loggable
    public Set<ScopeResponse> getAllScopes() {
        try {
            List<Scope> scopes = this.scopeRepository.findAll();

            return scopes.stream()
                    .map(this.mapper::mapScopeEntityToScopeResponse)
                    .collect(Collectors.toSet());
        } catch (DataAccessException ex) {
            throw new ProcessException("Error while retrieving all scopes: " + ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Loggable
    public ScopeResponse getScopeById(Long id) {
        try {
            Scope scope = this.scopeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Scope with ID " + id + " not found."));

            return this.mapper.mapScopeEntityToScopeResponse(scope);
        } catch (DataAccessException e) {
            throw new ProcessException("Error while retrieving scope with ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @Loggable
    public CreateScopeResponse createScope(CreateScopeRequest request) {
        try {
            boolean exists = this.scopeRepository.existsScopeByName((request.getScopeName()));

            if (exists) throw new BusinessException("Scope with name " + request.getScopeName() + " already exists.");

            User user = this.userRepository.findAll().stream().findFirst().orElseThrow(() -> new NotFoundException("No user found."));

            Scope scope = this.mapper.mapCreateScopeRequestToScopeEntity(request);
            scope.setCreatedBy(user);

            scope = this.scopeRepository.save(scope);

            return this.mapper.mapScopeEntityToCreateScopeResponse(scope);
        } catch (DataAccessException ex) {
            throw new ProcessException("Error while creating scope: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    @Loggable
    public boolean setUsersToScope(Long scopeId, Set<String> emailsRequest) {
        try {
            Scope scope = this.scopeRepository.findById(scopeId)
                    .orElseThrow(() -> new NotFoundException("Scope with ID " + scopeId + " not found."));

            Set<User> users = this.userRepository.findByEmailInIgnoreCase(emailsRequest);

            if (emailsRequest.size() != users.size()) {
                Set<String> existingEmails = users.stream()
                        .map(User::getEmail)
                        .collect(Collectors.toSet());

                Set<String> notFoundEmails = new HashSet<>(emailsRequest);
                notFoundEmails.removeAll(existingEmails);

                throw new NotFoundException("Users with emails " + notFoundEmails + " not found.");
            } else if (CollectionUtils.isEmpty(users)) {
                throw new NotFoundException("No users found for the provided emails.");
            }

            scope.setUsers(new ArrayList<>(users));
            this.scopeRepository.save(scope);

            return true;
        } catch (DataAccessException ex) {
            throw new ProcessException("Error while updating users for scope: " + ex.getMessage());
        }
    }
}