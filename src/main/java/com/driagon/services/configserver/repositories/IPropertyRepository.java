package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface IPropertyRepository extends JpaRepository<Property, Long> {

    Set<Property> findByScope_NameIgnoreCaseAndKeyNotInIgnoreCase(String scope_name, Collection<String> key);
}