package com.blinder.api.user.repository;

import com.blinder.api.user.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GenderRepository extends JpaRepository<Gender, String> {
    boolean existsByName(String name);

    Gender findByName(String name);
}
