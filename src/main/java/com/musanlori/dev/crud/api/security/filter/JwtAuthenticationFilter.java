package com.musanlori.dev.crud.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.security.model.GeneralAuthResponse;
import com.musanlori.dev.crud.api.security.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    /**
     * Use dependency injection with the AuthenticationManager configured in the SpringSecurityConfig class.
     * @param authenticationManager dependency.
     * */
    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * executes the user Authentication using its credentials.
     * @param request request object.
     * @param response response object.
     * @return {@link Authentication} authentication result.
     * */
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        UserRequest user = null;
        String username = null;
        String passwd = null;

        try {
            // a partir del HttpServletRequest, mapea los datos en el UserRequest
            user = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);
            username = user.getUsername();
            passwd = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, passwd);
        return authenticationManager.authenticate(authenticationToken);

    }

    /**
     * callback when the auth is success.
     * @param request request
     * @param response response
     * @param chain chain
     * @param authResult authResul
     * */
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        // extraccion del numbre de usuario
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();

        // extraccion de roles
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims()
                .add(Constants.AUTHORITIES_KEY, new ObjectMapper().writeValueAsString(roles))
                .build();

        // genera el token JWT a partir de los datos de usuario y la secret Key
        // el token dura una hora.
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + Constants.TIME_TO_EXPIRE))
                .issuedAt(new Date())
                .signWith(Constants.SECRET_KEY)
                .compact();

        // agrega el token al encabezado de la respuesta.
        response.addHeader(HttpHeaders.AUTHORIZATION, Constants.BEARER_PREFIX + token);

        // agrega el token en el body del response
        GeneralAuthResponse body = new GeneralAuthResponse(
                username,
                Constants.SUCCESS_LOGIN_MSG,
                token
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(Constants.CONTENT_TYPE_JSON);
        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * callback when the authentication fails.
     * @param request request
     * @param response response
     * @param failed authResul
     * */
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                              final AuthenticationException failed) throws IOException, ServletException {
        GeneralAuthResponse body = new GeneralAuthResponse(Constants.ERROR_LOGIN_MSG, failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(Constants.CONTENT_TYPE_JSON);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
