package by.application.Twitter.service;

import by.application.Twitter.model.LoginDetails;
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
        int countOfUsers = allUsers.size();
        user.setId(countOfUsers + 1);
        allUsers.add(user);
        return true;
    }

    @Override
    public boolean existUserByCredentials(LoginDetails loginDetails) {
        for (User user: allUsers) {
            if (user.getUsername().equals(loginDetails.getUsername()) && user.getPassword().equals(loginDetails.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return allUsers;
    }
}
