package com.musanlori.dev.crud.api.config;

import com.musanlori.dev.crud.api.core.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityCofig {

    /**
     * generates new password encoder.
     * @return bean of BCryptPasswordEncoder
     * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * configuration for allow to access at paths.
     * @param http param
     * @return {@link SecurityFilterChain} object
     * */
    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
            (authz) -> authz.requestMatchers(Constants.AUTH_BASE_PATH + "**")
                    .permitAll() //all in AUTH path, is public
                    .anyRequest()
                    .authenticated()) // the rest, is only with authenticate
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
