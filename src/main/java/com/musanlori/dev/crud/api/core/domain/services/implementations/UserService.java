package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.core.application.models.response.UserResponse;
import com.musanlori.dev.crud.api.core.domain.model.entity.RolesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.UsersEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IUserService;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.errors.exceptions.ServiceFlowErrorException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.RolesCrudRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.UsersCrudRepository;
import com.musanlori.dev.crud.api.core.util.Constants;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import com.musanlori.dev.crud.api.core.util.ObjectsDebuggerLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UsersCrudRepository userRepository;

    @Autowired
    private RolesCrudRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     * */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        log.info("Execute: find_all method");
        List<UsersEntity> results = (List<UsersEntity>) userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();

        for (UsersEntity result: results) {
            UserResponse model = new UserResponse();
            model.setUsername(result.getUsername());
            model.setEnable(result.getEnable());
            result.getRoles().forEach(rol -> model.getRole().add(rol.getName()));
            response.add(model);
        }

        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserResponse save(final UserRequest user) {
        log.info("Execute: save method");
        ObjectsDebuggerLog.showInJsonFormat(user);

        // todo: delete this. validacion auxiliar
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    String.format("the username %s already exists", user.getUsername()));
        }

        Optional<RolesEntity> optUser = roleRepository.findByName(Constants.ROLE_USER);
        List<RolesEntity> roles = new ArrayList<>();

        optUser.ifPresent(roles::add);

        if (user.isAdmin()) {
            log.info("adding role admin");
            Optional<RolesEntity> optAdmin = roleRepository.findByName(Constants.ROLE_ADMIN);
            optAdmin.ifPresent(roles::add);
        }

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(user.getUsername());
        usersEntity.setPassword(passwordEncoder.encode(user.getPassword())); //encode password
        usersEntity.setRoles(roles);
        usersEntity.setEnable(true);
        usersEntity.setCreatedAt(new Date());

        try {
            return setResponse(userRepository.save(usersEntity));
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new ServiceFlowErrorException(
                    ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                    ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                    "user"
            );
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean existByUsername(final String username) {
        log.debug("USERNAME_EXISTS?: {}", userRepository.existsByUsername(username));
        return userRepository.existsByUsername(username);
    }

    private UserResponse setResponse(final UsersEntity entity) {
        log.info("setting response");
        UserResponse response = new UserResponse();
        response.setUsername(entity.getUsername());
        response.setEnable(entity.getEnable());
        entity.getRoles().forEach(rol -> response.getRole().add(rol.getName()));
        return response;
    }
}
