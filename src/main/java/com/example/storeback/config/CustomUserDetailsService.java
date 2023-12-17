package com.example.storeback.config;


import com.example.storeback.exception.WrongUsernameOrEmail;
import com.example.storeback.model.User;
import com.example.storeback.repository.UserRepository;
import com.example.storeback.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user;
        if(Helper.isValidEmail(username)){
            user=userRepository.findUserByEmail(username);
        }else {
            user=userRepository.findUserByUsername(username);
        }

        if(user.isEmpty()){
            throw new WrongUsernameOrEmail("Invalid username!Please enter username or email again!");
        }


        return CustomUserDetails
                .builder()
                .userId(user.get().getId())
                .username(user.get().getUsername())
                .email(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(getAuthoritiesFromUser(user.get()))
                .build();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromUser(User user){
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }
}
