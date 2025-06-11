package com.musanlori.dev.crud.api.security.config;

import com.musanlori.dev.crud.api.core.util.Constants;
import com.musanlori.dev.crud.api.security.filter.JwtAuthenticationFilter;
import com.musanlori.dev.crud.api.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityCofig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * auth manager setup from AuthenticationConfiguration component.
     * @return {@link AuthenticationManager}
     * */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * generates new password encoder.
     * @return bean of BCryptPasswordEncoder
     * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * configuration for allow to access at paths according to the user role.
     * @param http httpSecurity implicit object.
     * @return {@link SecurityFilterChain} filter.
     * */
    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
            (authz) -> authz
                    .requestMatchers(Constants.AUTH_BASE_PATH + "**")
                    .permitAll() //all in AUTH path, is public
                    .requestMatchers(HttpMethod.GET, Constants.CRUD_BASE_PATH + "**")
                    .hasAnyRole(Constants.ADMIN_STR, Constants.USER_STR) //all POST in CRUD path is only to admin.
                    .requestMatchers(HttpMethod.POST, Constants.CRUD_BASE_PATH + "**")
                    .hasRole(Constants.ADMIN_STR) //all POST in CRUD path is only to admin.
                    .requestMatchers(HttpMethod.PUT, Constants.CRUD_BASE_PATH + "**")
                    .hasRole(Constants.ADMIN_STR) //all PUT in CRUD path is only to admin.
                    .requestMatchers(HttpMethod.DELETE, Constants.CRUD_BASE_PATH + "**")
                    .hasRole(Constants.ADMIN_STR) //all DELETE in CRUD path is only to admin.
                    .anyRequest()
                    .authenticated()) // the rest, is only with authenticate
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager())) // configura el login para generar jwt
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager())) // configura el validador de JWT
                .csrf(AbstractHttpConfigurer::disable) // desactiva el filtro de seguridad csrf.
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
