package com.admolodtsov.spring.springboot.trip_diary.config;


import com.admolodtsov.spring.springboot.trip_diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()

                //Доступ для всех
                .antMatchers("/","/images/**","/about").permitAll()
                //Доступ только для НЕзарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()
                //Доступ только для админа
                .antMatchers("/admin/**").hasRole("ADMIN")
                //Доступ только для зарегистрированных пользователей и админа
                .antMatchers("/trips/my/add").hasAnyRole("USER","ADMIN")
                //Доступ только для зарегистрированных пользователей
                .antMatchers("/trips/my/{id}").hasRole("USER")
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }


}