package by.application.Twitter.controller;

import by.application.Twitter.controller.dataTransferObject.PostDto;
import by.application.Twitter.controller.dataTransferObject.PostMapper;
import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.Post;
import by.application.Twitter.model.User;
import by.application.Twitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController extends BaseController {
    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/post")
    public ResponseEntity<?> createPost(@PathVariable(name = "userId") int userId, @Valid @RequestBody PostDto postDto) {
        User user = getUserNameFromToken();
        if(user.getId() == userId) {
            Post post = PostMapper.ToPost(postDto, user.getId());
            postService.createPost(post);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "postId") int postId, @PathVariable(name = "userId") int userId) {
        if(postService.isUserIdCorrectByPostId(userId, postId)) {
            Post post = postService.getPostById(postId);
            return post != null
                    ? new ResponseEntity(post, HttpStatus.OK)
                    : new ResponseEntity("Post not found", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<?> updatePostById(@PathVariable(name = "postId") int postId, @PathVariable(name = "userId") int userId, @Valid @RequestBody PostDto postDto) {
        User user = getUserNameFromToken();
        if(user.getId() == postService.getUserIdByPostId(postId) && postService.isUserIdCorrectByPostId(userId, postId)) {
            Post post = PostMapper.ToPost(postDto, user.getId());
            post.setId(postId);
            return postService.updatePost(post)
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity("Post not found", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable(name = "postId") int postId, @PathVariable(name = "userId") int userId) {
        User user = getUserNameFromToken();
        if(user.getId() == postService.getUserIdByPostId(postId) && postService.isUserIdCorrectByPostId(userId, postId)) {
            postService.deletePostById(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return new ResponseEntity(allPosts, HttpStatus.BAD_REQUEST);
    }
}
