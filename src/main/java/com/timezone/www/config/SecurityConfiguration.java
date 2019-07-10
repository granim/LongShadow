package com.timezone.www.config;

import com.timezone.www.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/",
                        "/index.html",
                        "index.html",
                        "/layout.html",
                        "/console/**",
                        "/fragments/**",
                        "/static/**",
                        "/static/css/**",
                        "/images/**",
                        "/webjars/**",
                        "/**/*.js", "/**/*.css"
                ).permitAll().anyRequest().permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/coworkers/**").authenticated()
                .antMatchers(HttpMethod.POST, "/coworkers/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/clients/**").authenticated()
                .antMatchers(HttpMethod.POST, "/clients/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/users/**").authenticated()
                .antMatchers(HttpMethod.POST, "/users/**").authenticated()
                .and()
                .requestCache().requestCache(new NullRequestCache())
                .and()
                .authorizeRequests()
                .antMatchers( "/workers/**", "/coworkers/**", "/clients/**", "users/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }



    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/static/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**");
    }


}
