package com.personal.JavaAssignment.service;

import com.personal.JavaAssignment.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

    void save (Users user);

    void deleteUserById(Long id);

    Users findUserByUsername(String username);

    Users findUserById(Long id);
}
