package ru.tayviscon.tayvisconbackend.team.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String username) {
        this("Не удалось найти профиль участника с именем пользователя '%s'", username);
    }

    public MemberNotFoundException(String message, Object ... args) {
        super(String.format(message, args));
    }
}
