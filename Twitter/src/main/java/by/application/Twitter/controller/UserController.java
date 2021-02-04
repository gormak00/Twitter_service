package by.application.Twitter.controller;

import by.application.Twitter.controller.dataTransferObject.UserDto;
import by.application.Twitter.controller.dataTransferObject.UserMapper;
import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;
import by.application.Twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<?> allUsers() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") int id) {
        User userFromToken = getUserNameFromToken();
        if (userFromToken.getId() == id) {
            User user = userService.getUserById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id, @Valid @RequestBody UserDto userDto) {
        User userToUpdate = UserMapper.toUser(userDto);
        User userFromToken = getUserNameFromToken();
        if (userFromToken.getId() == id) {
            userService.updateUser(userToUpdate, id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") int id) {
        User userFromToken = getUserNameFromToken();
        if (userFromToken.getId() == id) {
            userService.deleteUserById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/user/{id}/favorites", params = {"page", "size"})
    public ResponseEntity<?> getAllFavoritesPosts(@PathVariable(name = "id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        User userFromToken = getUserNameFromToken();
        if (userFromToken.getId() == id) {
            List<Post> favoritesPosts = userService.getAllFavoritesPostsByUserId(id, page, size);
            return new ResponseEntity(favoritesPosts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
