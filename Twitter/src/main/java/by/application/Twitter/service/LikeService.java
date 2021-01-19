package by.application.Twitter.service;

import by.application.Twitter.model.Like;
import by.application.Twitter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikeService {
    /**
     * Создание лайка
     *
     * @param userId - id пользователя
     * @param postId - id поста
     * @return false - если лайк уже стоял
     */
    boolean createLike(int postId, int userId);

    /**
     * Удаление лайка
     *
     * @param userId - id пользователя
     * @param postId - id поста
     * @return false - если лайк не стоял
     */
    boolean deleteLike(int postId, int userId);

    /**
     * Вернуть все посты с лайками проставленные пользователем
     *
     * @param userId   - id пользователя
     * @param pageable - параметры страницы на которой распределить посты
     * @return Page<Like> - страницу с лайками
     */
    Page<Like> getAllLikesByUserId(int userId, Pageable pageable);

    /**
     * Вернуть всех пользователей пролайкавших пост по postId
     *
     * @param postId - id пользователя
     * @return List<User> - список пользователей
     */
    List<User> getAllUsersByLikeOnPost(int postId);
}
