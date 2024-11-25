package com.example.SocialNetWork.services;

import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public Optional<UserEntity> findByLogin(String login){
        return userRepository.findByLogin(login);
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException(String
                        .format("Пользователь с таким логином не найдет")));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                        .collect(Collectors.toList()));
    }
}