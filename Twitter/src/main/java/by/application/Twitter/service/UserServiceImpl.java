package by.application.Twitter.service;

import by.application.Twitter.model.Like;
import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;
import by.application.Twitter.repository.UserRepository;
import by.application.Twitter.service.exception.InvalidCredentials;
import by.application.Twitter.service.exception.NotUnic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeService likeService;
    @Autowired
    private PostService postService;

    @Override
    public boolean createUser(User user) {
        List<User> allUsers = userRepository.findAll();
        //check to unic username
        for (User userFromDB : allUsers) {
            if (userFromDB.getUsername().equals(user.getUsername())) {
                throw new NotUnic();
            }
        }
        user.setId(allUsers.size() + 1);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean existUserByCredentials(LoginDetails loginDetails) {
        List<User> allUsers = userRepository.findAll();
        for (User user: allUsers) {
            if (user.getUsername().equals(loginDetails.getUsername()) && user.getPassword().equals(loginDetails.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        User user = userRepository.findById(id);
        if(user == null) return null;
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) return null;
        return user;
    }

    @Override
    public List<Post> getAllFavoritesPostsByUserId(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Like> allLikes = likeService.getAllLikesByUserId(id, pageable);
        List<Post> allPosts = new ArrayList<>();
        for (Like currentLike: allLikes.getContent()) {
            allPosts.add(postService.getPostById(currentLike.getPostId()));
        }
        return allPosts;
    }

    @Override
    public void updateUser(User userToUpdate, int id) {
        for (User userFromDB : userRepository.findAll()) {
            if (userFromDB.getUsername().equals(userToUpdate.getUsername()) /*&& !userToUpdate.getUsername().equals(getUserById(id).getUsername())*/) {
                throw new NotUnic();
            }
        }
        userRepository.updateUser(userToUpdate.getUsername(), userToUpdate.getPassword(), userToUpdate.getFirstName(), userToUpdate.getLastName(), userToUpdate.getEmail(), id);
    }

    @Override
    public boolean deleteUserById(int id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public void saveUserFromGoogle(User user){
        if (user == null){
            throw new InvalidCredentials();
        }
        for (User userFromDB : userRepository.findAll()) {
            if (userFromDB.getUsername().equals(user.getUsername())) {
                throw new NotUnic();
            }
        }
        final int userId = (int) userRepository.count() + 1;
        user.setId(userId);
        userRepository.save(user);
    }
}
