package com.telran.hiducation.config;

import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.security.CustomUserDetailsService;
import com.telran.hiducation.security.filter.JwtAuthenticationFilter;
import com.telran.hiducation.security.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final Environment environment;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic()
//                .and()
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), environment, userRepository, userDetailsService()))
                .authorizeRequests()
                .antMatchers("user/registration").permitAll()
                .antMatchers("user/registration/{hash}").permitAll()
                .antMatchers("/applications/**").permitAll()
//                .mvcMatchers("user/{userEmail}").hasRole("ADMIN")
//                .mvcMatchers("user/password/reset").hasRole("USER")
//                .antMatchers( "user/all").hasRole("USER")
                .mvcMatchers("/swagger-ui/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        ;
    }

    @SneakyThrows
    private JwtAuthenticationFilter getAuthenticationFilter() {
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager(), environment);
//        authenticationFilter.setFilterProcessesUrl("/user/login");
        String urlLogin = "/" + environment.getProperty("endpoint.url.user.controller") + "/" + environment.getProperty("endpoint.url.user.login");
        authenticationFilter.setFilterProcessesUrl(urlLogin);
        return authenticationFilter;
    }
}
