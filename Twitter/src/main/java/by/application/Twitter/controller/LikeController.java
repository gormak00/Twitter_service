package by.application.Twitter.controller;

import by.application.Twitter.model.User;
import by.application.Twitter.service.LikeService;
import by.application.Twitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LikeController extends BaseController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private PostService postService;

    @PostMapping(value = "/user/{userId}/post/{postId}/like")
    public ResponseEntity<?> createLikeOnPost(@PathVariable(name = "userId") int userId, @PathVariable(name = "postId") int postId) {
        if (postService.isUserIdCorrectByPostId(userId, postId)) {
            User user = getUserNameFromToken();
            return likeService.createLike(postId, user.getId())
                    ? new ResponseEntity(HttpStatus.OK)
                    : new ResponseEntity("Already like", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/user/{userId}/post/{postId}/like")
    public ResponseEntity<?> deleteLikeOnPost(@PathVariable(name = "userId") int userId, @PathVariable(name = "postId") int postId) {
        if (postService.isUserIdCorrectByPostId(userId, postId)) {
            User user = getUserNameFromToken();
            return likeService.deleteLike(postId, user.getId())
                    ? new ResponseEntity(HttpStatus.OK)
                    : new ResponseEntity("There was no like", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/user/{userId}/post/{postId}/like")
    public ResponseEntity<?> getAllLikesFromPost(@PathVariable(name = "userId") int userId, @PathVariable(name = "postId") int postId) {
        if (postService.isUserIdCorrectByPostId(userId, postId)) {
            List<User> allUsers = likeService.getAllUsersByLikeOnPost(postId);
            return new ResponseEntity(allUsers, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
