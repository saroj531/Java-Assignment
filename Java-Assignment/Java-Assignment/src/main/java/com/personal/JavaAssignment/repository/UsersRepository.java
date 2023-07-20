package com.personal.JavaAssignment.repository;

import com.personal.JavaAssignment.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findUserById(Long id);

    Users findUserByUsername(String username);

}
