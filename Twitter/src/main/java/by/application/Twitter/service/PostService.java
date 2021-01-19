package by.application.Twitter.service;

import by.application.Twitter.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    /**
     * Создание поста
     *
     * @param post - пост для создания
     */
    void createPost(Post post);

    /**
     * Вернуть пост по его id
     *
     * @param id - id пользователя
     * @return Post - найденный пост по id
     */
    Post getPostById(int id);

    /**
     * Вернуть автора поста
     *
     * @param id - id поста
     * @return userId - id автора поста
     */
    int getUserIdByPostId(int id);

    /**
     * Обновить содержимое поста
     *
     * @param post - новый пост
     * @return true - если пост обновлен
     */
    boolean updatePost(Post post);

    /**
     * Удалить пост по id
     *
     * @param id - id поста
     */
    void deletePostById(int id);

    /**
     * Вернуть все посты
     *
     * @param page - текущая страница
     * @param size - колличество результатов на странице
     * @return Page<Post> - список постов расположенных на странице
     */
    Page<Post> getAllPosts(int page, int size);

    /**
     * Сверка userId и postId у поста
     *
     * @param userId - id пользователя
     * @param postId - id поста
     * @return true - если этот пост пользователя
     */
    boolean isUserIdCorrectByPostId(int userId, int postId);
}
