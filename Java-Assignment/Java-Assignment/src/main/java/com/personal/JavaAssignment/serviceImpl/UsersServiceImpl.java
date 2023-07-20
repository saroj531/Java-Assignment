package com.personal.JavaAssignment.serviceImpl;

import com.personal.JavaAssignment.entity.Roles;
import com.personal.JavaAssignment.entity.Users;
import com.personal.JavaAssignment.repository.RolesRepository;
import com.personal.JavaAssignment.repository.UsersRepository;
import com.personal.JavaAssignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository){
        super();
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void save(Users user) {
        System.out.println(user);
        Users newUser = new Users();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if(rolesRepository.findRoleByName("ROLE_USER") != null)
            newUser.setRoles(Arrays.asList(rolesRepository.findRoleByName("ROLE_USER")));
        else
            newUser.setRoles(Arrays.asList(new Roles("ROLE_USER")));

        usersRepository.save(newUser);
    }

    @Override
    public void deleteUserById(Long id){
        this.usersRepository.deleteById(id);
    }

    @Override
    public Users findUserById(Long id){
        return usersRepository.findUserById(id);
    }

    @Override
    public Users findUserByUsername(String username){
        return usersRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findUserByUsername(username);


        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
