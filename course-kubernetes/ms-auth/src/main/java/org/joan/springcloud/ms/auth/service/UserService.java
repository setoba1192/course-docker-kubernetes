package org.joan.springcloud.ms.auth.service;

import org.joan.springcloud.ms.auth.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private WebClient webClient;

    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserDTO userDTO = webClient.get().uri("http://ms-users:8001/login", uri -> uri.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();

            log.info("Authenticated User email: "+userDTO.getEmail());
            log.info("Authenticated name: "+userDTO.getName());

            return User.builder()
                    .username(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .credentialsExpired(false)
                    .accountExpired(false)
                    .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                    .build();

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("Login error, user " + email + " doesn't exist");
        }
    }
}
