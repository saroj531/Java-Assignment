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
        Users newUser = new Users(user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getUsername(), passwordEncoder.encode(user.getPassword()), Arrays.asList(new Roles("ROLE_USER")));
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
