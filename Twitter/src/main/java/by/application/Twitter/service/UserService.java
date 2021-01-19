package by.application.Twitter.service;

import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;

import java.util.List;

public interface UserService {
    /**
     * Создание пользователя
     *
     * @param user пользователь, которого необходимо добавить
     * @return true - если пользователь добавлен, false - если пользователь не добавлен
     */
    boolean createUser(User user);

    /**
     * Проверка на существование пользователя по данным для входа
     *
     * @param loginDetails - данные для входа (username и password)
     * @return true - если такой пользователь существует, false - если такой пользователь не существует
     */
    boolean existUserByCredentials(LoginDetails loginDetails);

    /**
     * Возврат списка всех пользователей
     *
     * @return List<User> - список всех пользователей
     */
    List<User> getAllUsers();

    /**
     * Возврат пользователя по id
     *
     * @param id - id пользователя
     * @return User - пользователь с заданным id, null - если такого пользователя нет
     */
    User getUserById(int id);

    /**
     * Возврат пользователя по username
     *
     * @param username - username пользователя
     * @return User - пользователь с заданным username, null - если такого пользователя нет
     */
    User getUserByUsername(String username);

    /**
     * Возврат понравившихся постов пользователя
     *
     * @param id   - id пользователя
     * @param page - текущая страница
     * @param size - колличество результатов на странице
     * @return List<Post> - список постов
     */
    List<Post> getAllFavoritesPostsByUserId(int id, int page, int size);
}
