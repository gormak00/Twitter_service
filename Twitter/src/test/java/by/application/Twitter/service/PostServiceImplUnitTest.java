package by.application.Twitter.service;

import by.application.Twitter.model.Post;
import by.application.Twitter.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PostServiceImplUnitTest {
    @TestConfiguration
    static class PostServiceImplTestContextConfiguration {

        @Bean
        public PostService postService() {
            return new PostServiceImpl();
        }
    }

    @Autowired
    private PostService postService;
    @MockBean
    private PostRepository postRepository;

    private Post post1User1, post2User1, post3User2;
    private List<Post> allPosts;
    private Pageable pageable;
    private Page<Post> postPage;

    @BeforeEach
    public void setUp() {
        post1User1 = new Post(1, 1, "first post");
        post2User1 = new Post(2, 1, "second post");
        post3User2 = new Post(3, 2, "third post");
        allPosts = new ArrayList<>();
        pageable = PageRequest.of(0, 2);
        allPosts.add(post1User1);
        allPosts.add(post2User1);
        postPage = new PageImpl<Post>(allPosts);

        Mockito.when(postRepository.count())
                .thenReturn((long) 2);
        Mockito.when(postRepository.save(post3User2))
                .thenReturn(post3User2);
        Mockito.when(postRepository.findById(1))
                .thenReturn(post1User1);
        Mockito.when(postRepository.findById(2))
                .thenReturn(post2User1);
        Mockito.when(postRepository.updatePostById(post3User2.getData(), post3User2.getId()))
                .thenReturn(1);
        Mockito.when(postRepository.findAll(pageable))
                .thenReturn(postPage);
        Mockito.when(postRepository.findByIdAndUserId(post1User1.getId(), post1User1.getUserId()))
                .thenReturn(post1User1);
        Mockito.doNothing().when(postRepository).deleteById(post1User1.getId());
    }

    @Test
    public void createPost() {
        //when
        postService.createPost(post3User2);
        // then
        Assertions.assertThat(postRepository.count())
                .isEqualTo(2);
    }

    @Test
    public void getPostById() {
        //when
        Post post = postService.getPostById(1);
        // then
        Assertions.assertThat(post.getData())
                .isEqualTo(post1User1.getData());
    }

    @Test
    public void getUserIdByPostId() {
        //when
        int userId = postService.getUserIdByPostId(2);
        // then
        Assertions.assertThat(userId)
                .isEqualTo(1);
    }

    @Test
    public void updatePost() {
        //when
        boolean status = postService.updatePost(post3User2);
        // then
        Assertions.assertThat(status)
                .isTrue();
    }

    @Test
    public void getAllPosts() {
        //when
        Page<Post> postPageResult = postService.getAllPosts(0, 2);
        // then
        Assertions.assertThat(postPageResult)
                .isEqualTo(postPage);
    }

    @Test
    public void isUserIdCorrectByPostId() {
        //when
        boolean status = postService.isUserIdCorrectByPostId(1, 1);
        // then
        Assertions.assertThat(status)
                .isTrue();
    }
}
