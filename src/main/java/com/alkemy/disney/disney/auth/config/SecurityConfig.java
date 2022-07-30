package com.alkemy.disney.disney.auth.config;

import com.alkemy.disney.disney.auth.filter.JwtRequestFilter;
import com.alkemy.disney.disney.auth.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtRequestFilter JwtRequestFilter(){
        return new JwtRequestFilter();
    }

    private String[] WHITE_LIST = {"/auth/login", "/auth/register", "/auth/refresh",
            "/auth/*", "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/**",
            "/v2/**"
    };

    // Password Encoding
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    // Security Configurations
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new JwtRequestFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
