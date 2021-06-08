package com.bulitski.app.configuration;

import com.bulitski.app.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserServiceImpl userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user/edit","/user/new","/lock")
//                .hasAnyAuthority("ADMIN")
//                .antMatchers("/user", "/view",  "/basket","/user/**")
//                .hasAnyAuthority("USER", "ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .failureUrl("/login?error").defaultSuccessUrl("/Error")
//                .loginPage("/login")
//                .defaultSuccessUrl("/user",true)
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login");
        http
                .csrf().disable()
                .authorizeRequests()
        .antMatchers("/user/edit","/user/new","/lock")
                .hasAnyAuthority("ADMIN")
                .antMatchers("/user", "/view",  "/basket","/user/**")
                .hasAnyAuthority("USER", "ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user", true)
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutSuccessUrl("/login");
    }


    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}
