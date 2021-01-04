package by.application.Twitter.service;

import by.application.Twitter.model.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public boolean createUser(User user);
}
