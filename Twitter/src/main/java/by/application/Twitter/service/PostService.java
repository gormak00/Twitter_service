package by.application.Twitter.service;

import by.application.Twitter.model.Post;

import java.util.List;

public interface PostService {
    void createPost(Post post);

    Post getPostById(int id);

    int getUserIdByPostId(int id);

    boolean updatePost(Post post);

    void deletePostById(int id);

    List<Post> getAllPosts();

    boolean isUserIdCorrectByPostId(int userId, int postId);
}
