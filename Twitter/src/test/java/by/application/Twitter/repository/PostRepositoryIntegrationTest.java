package by.application.Twitter.repository;

import by.application.Twitter.model.Post;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private Post post1User1, post2User1, post3User2;

    @Before
    public void beforeAll() {
        post1User1 = new Post(1, 1, "first post");
        post2User1 = new Post(2, 1, "second post");
        post3User2 = new Post(3, 2, "third post");
        entityManager.persist(post1User1);
        entityManager.persist(post2User1);
        entityManager.persist(post3User2);
        entityManager.flush();
    }

    @Test
    public void getPostByPostId() {
        // when
        Post postFromRepo = postRepository.findById(1);

        // then
        Assertions.assertThat(postFromRepo.getData())
                .isEqualTo(post1User1.getData());
    }

    @Test
    public void updatePostById() {
        // when
        int count = postRepository.updatePostById("new Info", 2);

        // then
        Assertions.assertThat(count)
                .isEqualTo(1);
    }

    @Test
    public void isThisUsersPost() {
        // when
        Post postFromRepo = postRepository.findByIdAndUserId(2, 1);

        // then
        Assertions.assertThat(postFromRepo.getData())
                .isEqualTo(post2User1.getData());
    }

    @Test
    public void findAllToList() {
        // when
        List<Post> allPosts = postRepository.findAll();

        // then
        Assertions.assertThat(allPosts.size())
                .isEqualTo(3);
    }
}
