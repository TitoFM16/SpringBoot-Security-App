package com.tito.userservice.security;

import com.tito.userservice.filter.CustomAuthenticationFilter;
import com.tito.userservice.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfiguration {



    private final AuthenticationManagerBuilder authManagerBuilder;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //----------------HTTP SECURITY CONFIG-------------------------------------
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //this customizes the url for login
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        //http config

        //using JWT
        http.csrf().disable();

        //don't create session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Authorized Requests
        http.authorizeRequests().antMatchers(GET, "/api/login/**","/api/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        //Filters
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        //http.addFilter(new CustomAuthenticationFilter(authManagerBuilder.getOrBuild()));

        return http.build();
    }

    //----------------HTTP SECURITY CONFIG-------------------------------------



}