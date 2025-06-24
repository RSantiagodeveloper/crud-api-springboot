package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.core.application.models.response.UserResponse;
import com.musanlori.dev.crud.api.core.domain.model.entity.RolesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.UsersEntity;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.errors.exceptions.ServiceFlowErrorException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.RolesCrudRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.UsersCrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UsersCrudRepository userRepository;

    @Mock
    private RolesCrudRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String username;

    private String rol_user_str;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.username = "USER_NAME";
        this.rol_user_str = "ROL_USER";
    }

    @Test
    void findAllTest1() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(generateEntity()));
        List<UserResponse> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        response.forEach(user -> {
            Assertions.assertEquals(username, user.getUsername());
            Assertions.assertFalse(user.getRole().isEmpty());
            user.getRole().forEach(rol -> Assertions.assertEquals(this.rol_user_str, rol));
        });
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void saveTest1(boolean isAdmin) {

        UserRequest request = new UserRequest();
        request.setUsername(this.username);
        request.setPassword("PASSWD");
        request.setAdmin(isAdmin);

        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(rolesEntity()));
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("ENCRYPTED_PASS");
        Mockito.when(userRepository.save(Mockito.any(UsersEntity.class))).thenReturn(generateEntity());
        UserResponse response = service.save(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.username, response.getUsername());
        Assertions.assertTrue(response.isEnable());
        Assertions.assertFalse(response.getRole().isEmpty());
    }

    @Test
    void saveTest2() {

        UserRequest request = new UserRequest();
        request.setUsername(this.username);
        request.setPassword("PASSWD");
        request.setAdmin(true);

        UsersEntity usersEntity = generateEntity();
        usersEntity.setRoles(new ArrayList<>());

        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("ENCRYPTED_PASS");
        Mockito.when(userRepository.save(Mockito.any(UsersEntity.class))).thenReturn(usersEntity);
        UserResponse response = service.save(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.username, response.getUsername());
        Assertions.assertTrue(response.isEnable());
        Assertions.assertTrue(response.getRole().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void saveFailTest1(boolean flag) {

        UserRequest request = new UserRequest();
        request.setUsername(this.username);
        request.setPassword("PASSWD");
        request.setAdmin(true);

        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("ENCRYPTED_PASS");
        Mockito.when(userRepository.save(Mockito.any(UsersEntity.class)))
                .thenThrow((flag) ? IllegalArgumentException.class: OptimisticLockingFailureException.class);
        Assertions.assertThrows(ServiceFlowErrorException.class, () -> service.save(request));
    }

    @Test
    void saveFailTest2() {

        UserRequest request = new UserRequest();
        request.setUsername(this.username);
        request.setPassword("PASSWD");
        request.setAdmin(true);

        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.save(request));
    }

    @Test
    void existByUsernameTest() {
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        boolean response = service.existByUsername(username);
        Assertions.assertTrue(response);
    }

    private UsersEntity generateEntity() {
        UsersEntity entity = new UsersEntity();
        entity.setId(1);
        entity.setUsername(username);
        entity.setEnable(true);
        entity.setPassword("MY_FCKN_PASS");
        entity.setRoles(List.of(rolesEntity()));
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return entity;
    }

    private RolesEntity rolesEntity() {
        RolesEntity rol = new RolesEntity();
        rol.setId(1);
        rol.setName(this.rol_user_str);
        rol.setCreatedAt(new Date());
        rol.setUpdatedAt(new Date());
        return rol;
    }

}