package com.whitehacker.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder pwEncoder;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select username, password, enabled from USERS where username = ?")
                .authoritiesByUsernameQuery("select username, role from USERS where username = ?")
                .dataSource(dataSource).passwordEncoder(pwEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                    .requestMatchers("/user/**").hasRole("USER")
                    .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/register/**").permitAll()
                    .requestMatchers("/logout/**").permitAll()
                    .requestMatchers("/CSS/**").permitAll()
                    .requestMatchers("/Images/**").permitAll()
                    .requestMatchers("/**").permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin().loginPage("/login");

        http.csrf().ignoringRequestMatchers("/h2-console/**");
        http.headers().frameOptions().disable();
        return http.build();
    }

}
