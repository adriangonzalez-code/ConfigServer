package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPropertyRepository extends JpaRepository<Property, Long> {
}