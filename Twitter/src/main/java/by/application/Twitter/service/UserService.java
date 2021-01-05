package by.application.Twitter.service;

import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public boolean createUser(User user);

    boolean existUserByCredentials(LoginDetails loginDetails);

    List<User> getAllUsers();
}
