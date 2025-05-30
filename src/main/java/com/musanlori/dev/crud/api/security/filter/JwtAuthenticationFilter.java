package com.musanlori.dev.crud.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
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

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    // generates the secret Key.
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    /**
     * constructor.
     * @param authenticationManager param.
     * */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * realiza la autenticacion del usuario usando sus credenciales.
     * implicitamente manda a llamar el servicio que implementa UserDetailsService de Springboot.
     * @param request req
     * @param response resp
     * @return {@link Authentication}
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
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
     * Metodo que se activa cuando la autenticacion fue exitosa.
     * @param request request
     * @param response response
     * @param chain chain
     * @param authResult authResul
     * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // extraccion del numbre de usuario
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();

        // extraccion de roles
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims().add("authorities", roles).build();

        // genera el token JWT a partir de los datos de usuario y la secret Key
        // el token dura una hora.
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        // agrega el token al encabezado de la respuesta.
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        // agrega el token en el body del response
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("msg", "Login Success");
        body.put("token", token);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la validacion de credenciales");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
