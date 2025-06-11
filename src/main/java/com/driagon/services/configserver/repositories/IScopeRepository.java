package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IScopeRepository extends JpaRepository<Scope, Long> {

    Optional<Scope> findScopeByName(String name);

    boolean existsScopeByName(String name);
}