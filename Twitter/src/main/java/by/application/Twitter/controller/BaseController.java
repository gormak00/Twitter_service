package by.application.Twitter.controller;

import by.application.Twitter.model.User;
import by.application.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    @Autowired
    private UserService userService;

    public User getUserNameFromToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (!username.isEmpty()) {
            return userService.getUserByUsername(username);
        }
        return null;
    }
}
