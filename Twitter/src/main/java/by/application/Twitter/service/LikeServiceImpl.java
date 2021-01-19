package by.application.Twitter.service;

import by.application.Twitter.model.Like;
import by.application.Twitter.model.User;
import by.application.Twitter.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserService userService;

    @Override
    public boolean createLike(int postId, int userId) {
        if(likeRepository.findByPostIdAndUserId(postId, userId) == null) {
            final int likeId = (int) (likeRepository.count() + 1);
            likeRepository.save(new Like(likeId, postId, userId));
            return true;
        } else return false;
    }

    @Override
    public boolean deleteLike(int postId, int userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId);
        if(like != null) {
            likeRepository.deleteById(like.getId());
            return true;
        } else return false;
    }

    @Override
    public Page<Like> getAllLikesByUserId(int userId, Pageable pageable) {
        return likeRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public List<User> getAllUsersByLikeOnPost(int postId) {
        List<Like> allLikesFromPost = likeRepository.findAllByPostId(postId);
        List<User> allUsers = new ArrayList<>();
        for (Like currentLike: allLikesFromPost) {
            allUsers.add(userService.getUserById(currentLike.getUserId()));
        }
        return allUsers;
    }
}
