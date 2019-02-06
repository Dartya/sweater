package com.example.sweater.config;

import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;


@Configuration      //класс конфигурации,
@EnableWebSecurity  //при старте приложения конфигурирует WebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Autowired
    private DataSource dataSource;*/
    @Autowired
    private UserService userService;

    @Override       //передает на вход объект
    protected void configure(HttpSecurity http) throws Exception {
        http    //, в объекте включаем: 1) авторизацию, 2) для пути "/" разрешаем полный доступ, 3) для остальных запросов требуем авторизацию
                //И (.and) 4) включаем формлогин, 5) подключаем форму входа по ссылке "/login", 6) разрешаем на нее заходить всем
                //И (.and) 7) включаем логаут, 8) позволяем им пользоваться всем
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.jdbcAuthentication()
                .dataSource(dataSource) //датасорс нужен для того, чтобы менеджер мог входить в БД и искать там пользователей и их роли
                .passwordEncoder(NoOpPasswordEncoder.getInstance())      //шифрует пароли, чтобы не хранить их в явном виде
                .usersByUsernameQuery("select username, password, active from usr where username=?")   //запрос необходим, чтобы система могла найти юзера по его имени
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");  //это запрос помогает спрингу найти список пользователей с их ролями
                */
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());    //используется для сохранения информации о пользователе
    }
    /*  этот метод более не используется, потому что мы берем пользователя из БД
    @Bean
    @Override
    public UserDetailsService userDetailsService() {    //1) юзердитейл сервис передается системе по требованию
        UserDetails user =                              //тут создаются пользователи, но создавать их руками не удобно
                User.withDefaultPasswordEncoder()       //поэтому идем в /domain и добавляем класс User
                        .username("u")
                        .password("1")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);    //2) он создает в памяти менеджер, обслуживающий учетные записи
    }
    */
}