package by.application.Twitter.service;

import by.application.Twitter.model.Post;
import by.application.Twitter.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public void createPost(Post post) {
        final int postId = (int) postRepository.count() + 1;
        post.setId(postId);
        postRepository.save(post);
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public int getUserIdByPostId(int id) {
        Post post = postRepository.findById(id);
        return post != null
                ? post.getUserId()
                : 0;
    }

    @Override
    public boolean updatePost(Post post) {
            int result = postRepository.updatePostById(post.getData(), post.getId());
            return result == 1;
    }

    @Override
    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public boolean isUserIdCorrectByPostId(int userId, int postId) {
        Post post = postRepository.findByIdAndUserId(postId, userId);
        return post != null;
    }
}
