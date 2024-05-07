package br.com.turbomotors.turbomotors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementarFuncDetailsService funcionarioAcao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/TurboStyle/***").permitAll()
                .antMatchers("/TurboStyle/img/**").permitAll()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/admin/login").permitAll()
                .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin/painel", true)
                .failureUrl("/admin/login-incorreto")
                .permitAll()
            .and()
            .logout()
                .logoutSuccessUrl("/admin/login")
                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                .permitAll(); 
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // System.out.println("ACAO >  =  " + acao);
        auth.userDetailsService(funcionarioAcao)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

}
