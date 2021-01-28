
package by.application.Twitter.service;

import by.application.Twitter.model.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;

public interface GoogleAuthService {

    /**
     * Создание map для текущего пользователя из google
     *
     * @param authentication данные полученные из google авторизации
     * @return userAttributes - данные текущего пользователя, хранящиеся в map
     */

    Map getUserAttributes(OAuth2AuthenticationToken authentication);


    /**
     * Создание объекта (User) текущего пользователя из google
     *
     * @param userAttributes данные текущего пользователя, хранящиеся в map
     * @return user - сам пользователь
     */

    User loadUser(Map userAttributes);


    /**
     * Создание username from email
     *
     * @param email - почта текущего пользователя
     * @return username - новый username
     */

    String getUsernameFromEmail(String email);


    /**
     * Создание random password
     *
     * @return password - новый пароль длинной в 8 символов
     */

    String generateRandomPassword();
}

