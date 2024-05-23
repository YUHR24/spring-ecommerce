package com.yuhr.ecommerce.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringBootSecurity {

    // @Autowired
    // private UserDetailsService userDetailsService;

    // @Autowired
    // private BCryptPasswordEncoder bCrypt;

    @Bean
    public UserDetailsService userDetailsService() {
        // Define and return your UserDetailsService implementation
        return userDetailsService()  ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        // authProvider.setPasswordEncoder(bCrypt);

        return authProvider;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()).authorizeRequests(requests -> requests
                .requestMatchers("/administrador/**").hasRole("ADMIN")
                .requestMatchers("/productos/**").hasRole("ADMIN")).formLogin(login -> login.loginPage("/usuario/login")
                .permitAll().defaultSuccessUrl("/usuario/acceder"));
 
        return http.build();
    }

}
