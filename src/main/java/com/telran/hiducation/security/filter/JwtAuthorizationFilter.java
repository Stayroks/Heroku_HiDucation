package com.telran.hiducation.security.filter;

import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.pojo.entity.RoleEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private Environment environment;
    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, Environment environment, UserRepository userRepository, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.environment = environment;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader) {
        String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

        String secretKey = environment.getProperty("token.secret.key");

        SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String username = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (username == null) {
            return null;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();

        return new UsernamePasswordAuthenticationToken(
                username, null, grantedAuthorities
        );
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles) {
        return roles.stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList());
    }
}
