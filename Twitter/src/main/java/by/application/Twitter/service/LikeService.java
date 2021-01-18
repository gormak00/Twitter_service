package by.application.Twitter.service;

import by.application.Twitter.model.Like;
import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;

import java.util.List;

public interface LikeService {
    boolean createLike(int postId, int userId);
    boolean deleteLike(int postId, int userId);
    List<Like> getAllLikesByUserId(int userId);
    List<User> getAllUsersByLikeOnPost(int postId);
}
