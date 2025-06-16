package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScopeRepository extends JpaRepository<Scope, Long> {

    boolean existsById(Long id);

    boolean existsScopeByName(String name);

    boolean existsScopeByNameAndAccessKey(String scopeName, String accessKey);
}