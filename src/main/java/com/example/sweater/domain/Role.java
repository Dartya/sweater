package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority { //перечисление, связано с пользователем, но не хранится в БД
    USER;

    @Override
    public String getAuthority() {
        return name();  //строковое представление значения USER;
    }
}
