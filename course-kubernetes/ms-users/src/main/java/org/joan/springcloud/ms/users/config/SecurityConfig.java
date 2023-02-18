package org.joan.springcloud.ms.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/authorized", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/", "/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
                        .requestMatchers(HttpMethod.POST, "/").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.PUT, "/{id}").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.DELETE, "/{id}").hasAuthority("SCOPE_write")
                        .anyRequest().authenticated()
                        .and())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/ms-users-client"))
                .csrf().disable()
                .oauth2Client(withDefaults())
                .csrf().disable()
                .oauth2ResourceServer().jwt();

        return httpSecurity.build();
    }
}
