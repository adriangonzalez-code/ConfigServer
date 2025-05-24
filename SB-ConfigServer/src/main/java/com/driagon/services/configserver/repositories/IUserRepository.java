package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}