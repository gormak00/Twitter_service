package by.application.Twitter.controller;

import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;
import by.application.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> allUsers() {
        userService.getAllUsers();

        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") int id) {
        User userFromToken = getUserNameFromToken();
        if (userFromToken.getId() == id) {
            User user = userService.getUserById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{id}/favorites")
    public ResponseEntity<?> getAllFavoritesPosts(@PathVariable(name = "id") int id) {
        User userFromToken = getUserNameFromToken();
        if (userFromToken.getId() == id) {
            List<Post> favoritesPosts = userService.getAllFavoritesPostsByUserId(id);
            return new ResponseEntity(favoritesPosts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
