package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity                 //сущность User, храним ее в БД
@Table(name = "usr")    //данной аннотацией указываем, в какой таблице хранить пользователей
public class User implements UserDetails {
    //поля
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //автогенерация id
    private long id;
    private String username;
    private String password;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)    //позволяет избавиться от работы по генерации таблицы для хранения enum
    //fetch - данный параметр определяет, как будет подгружаться параметр относительно основной сущности - FetchType.EAGER или FetchType.LAZY/ в первом случае подгрузятся сразу все роли, во втором - только когда обратится к связанному пользователю
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    //аннотация выше позволяет создать таблицу user_role для нижеследующего набора ролей и связать ее с текущей таблицей через user_id
    @Enumerated(EnumType.STRING)    //хотим хранить enum как STRING
    private Set<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();  //синонимы с методом класса
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
