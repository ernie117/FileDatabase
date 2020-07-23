package com.practice.work.films.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("#{@environment.getProperty('spring.profiles') ?: 'dev'}")
    private String env;

    @Value("#{@environment.getProperty('spring.security.user.name') ?: 'test_user'}")
    private String user;

    @Value("#{@environment.getProperty('spring.security.user.password') ?: 'test_pw'}")
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (env.equals("prod")) {
            auth.inMemoryAuthentication()
                    .passwordEncoder(new Argon2PasswordEncoder())
                    .withUser(user)
                    .password(String.format("{argon2}%s", password))
                    .roles("ADMIN");
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (env.equals("prod")) {
            log.info("HTTPS configured");
            httpSecurity
                    .csrf().disable()
                    .requiresChannel()
                    .anyRequest()
                    .requiresSecure()
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("ADMIN")
                    .and()
                    .httpBasic();
        } else {
            httpSecurity.csrf().disable();
        }
    }
}
