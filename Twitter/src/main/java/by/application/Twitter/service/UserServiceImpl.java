package by.application.Twitter.service;

import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.User;
import by.application.Twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean createUser(User user) {
        List<User> allUsers = userRepository.findAll();
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
}
