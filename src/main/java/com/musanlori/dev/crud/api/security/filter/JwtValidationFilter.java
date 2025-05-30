package com.musanlori.dev.crud.api.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.security.model.GeneralAuthResponse;
import com.musanlori.dev.crud.api.security.util.Constants;
import com.musanlori.dev.crud.api.security.util.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // to public endpoints that no required token or endpoints that no use bearer token.
        // continues the operation.
        if (header == null || !header.startsWith(Constants.BEARER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(Constants.BEARER_PREFIX, "");

        try {
            Claims payload = Jwts.parser().verifyWith(Constants.SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = payload.getSubject();
            Object authoritiesClaims = payload.get(Constants.AUTHORITIES_KEY);


            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class) // configura el conversor de campos
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } catch (JwtException e) {

            GeneralAuthResponse body = new GeneralAuthResponse(Constants.ERROR_JWT_MSG, e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(Constants.CONTENT_TYPE_JSON);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
