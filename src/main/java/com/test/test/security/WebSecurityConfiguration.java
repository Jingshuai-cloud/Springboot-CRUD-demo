package com.test.test.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST,"/users/add","/users/login").permitAll()
                .antMatchers(HttpMethod.GET,"/users", "/users/count").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/{id}").permitAll()
                .anyRequest().authenticated();
        //allow cross cors post
        http.cors();
    }



}