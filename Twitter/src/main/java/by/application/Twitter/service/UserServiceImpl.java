package by.application.Twitter.service;

import by.application.Twitter.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private List<User> allUsers;

    public UserServiceImpl(){
        allUsers = new ArrayList<>();
    }

    @Override
    public boolean createUser(User user) {
        allUsers.add(user);
        return true;
    }
}
