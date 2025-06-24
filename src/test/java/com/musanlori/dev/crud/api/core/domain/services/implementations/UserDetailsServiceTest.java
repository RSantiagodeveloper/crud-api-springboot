package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.domain.model.entity.RolesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.UsersEntity;
import com.musanlori.dev.crud.api.core.infrastructure.repository.UsersCrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsService service;

    @Mock
    private UsersCrudRepository repository;

    private String username;
    private String rol_user_str;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.username = "USER_NAME";
        this.rol_user_str = "ROL_USER_STR";
    }

    @Test
    void loadUserByUsernameTest1() {
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        UserDetails response = service.loadUserByUsername(username);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(username, response.getUsername());
        Assertions.assertTrue(response.isEnabled());
    }

    @Test
    void loadUserByUsernameTest2() {
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(username));
    }

    private UsersEntity generateEntity() {
        UsersEntity entity = new UsersEntity();
        RolesEntity rol = new RolesEntity();
        rol.setId(1);
        rol.setName(rol_user_str);
        rol.setCreatedAt(new Date());
        rol.setUpdatedAt(new Date());

        entity.setId(1);
        entity.setUsername(username);
        entity.setEnable(true);
        entity.setPassword("MY_FCKN_PASS");
        entity.setRoles(List.of(rol));
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return entity;
    }
}