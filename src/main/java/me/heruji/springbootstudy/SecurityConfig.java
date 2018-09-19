package me.heruji.springbootstudy;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            if (!"admin".equals(username)) {
                throw new UsernameNotFoundException(username);
            }
            return new Reader(username, "administrator", "{noop}123");
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/*").access("hasAuthority('READER')")
                .anyRequest().permitAll()

                .and()

                .formLogin()

                .and()

                .headers().frameOptions().disable()

                .and()

                .csrf().disable();
    }
}
