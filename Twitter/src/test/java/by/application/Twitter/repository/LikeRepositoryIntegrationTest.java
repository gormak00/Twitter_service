package by.application.Twitter.repository;

import by.application.Twitter.model.Like;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikeRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikeRepository likeRepository;

    private Like like1User1Post1, like2User1Post2, like3User2Post1;

    @Before
    public void beforeAll() {
        like1User1Post1 = new Like(1, 1, 1);
        like2User1Post2 = new Like(2, 1, 2);
        like3User2Post1 = new Like(3, 2, 1);
        entityManager.persist(like1User1Post1);
        entityManager.persist(like2User1Post2);
        entityManager.persist(like3User2Post1);
        entityManager.flush();
    }

    @Test
    public void getLikeByUserIdAndPostId() {
        //when
        Like likeFromRepo = likeRepository.findByPostIdAndUserId(1, 1);

        //then
        Assertions.assertThat(likeFromRepo.getId())
                .isEqualTo(like1User1Post1.getId());
    }

    @Test
    public void getAllLikesByPostId() {
        //when
        List<Like> allLikes = likeRepository.findAllByPostId(1);

        //then
        Assertions.assertThat(allLikes.size())
                .isEqualTo(2);
    }

    @Test
    public void getAllUsersIdByUserIdAndPostId() {
        //when
        List<Like> allLikesByUserId = likeRepository.findAllByUserId(1, PageRequest.of(0, 2)).getContent();

        //then
        Assertions.assertThat(allLikesByUserId.size())
                .isEqualTo(2);
    }
}
