package org.gfg.TransactionService.config;

import org.gfg.TransactionService.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf->csrf.disable());
      //  httpSecurity.authorizeHttpRequests(request->request.requestMatchers("*transaction-service/initiate/transaction*").hasRole("USER"));
        httpSecurity.authorizeHttpRequests(request-> request.anyRequest().authenticated());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        return customUserDetailsService;
    }

  /*  @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(new CustomUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return daoAuthenticationProvider;
    }*/


    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
