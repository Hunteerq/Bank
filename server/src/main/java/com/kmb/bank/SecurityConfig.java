package com.kmb.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/home","/password").permitAll()
                //.antMatchers("/password").hasRole("PRE_AUTH_USER")
                .antMatchers("/dashboard").hasRole("USER")
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/check")
                .permitAll()
                .failureUrl("/password?error")
                // always use the default success url despite if a protected page had been previously visited
                .defaultSuccessUrl("/dashboard", true)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("no").password(passwordEncoder.encode("siema")).roles("PRE_AUTH_USER")
                .and()
                .withUser("elo").password(passwordEncoder.encode("elo")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
