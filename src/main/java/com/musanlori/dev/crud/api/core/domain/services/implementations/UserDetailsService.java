package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.domain.model.entity.UsersEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IUsersDetailsService;
import com.musanlori.dev.crud.api.core.infrastructure.repository.UsersCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements IUsersDetailsService {

    @Autowired
    private UsersCrudRepository repository;

    /**
     * Inherit method from org.springframework.security.core.userdetails package.
     * @param username username
     * @return {@link UserDetails}
     * */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> byUsername = repository.findByUsername(username);

        UsersEntity user = byUsername.orElseThrow(() ->
                new UsernameNotFoundException(String.format("username %s does not exist", username)));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getEnable(),
                true,
                true,
                true,
                authorities
        );
    }
}
