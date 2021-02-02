package by.application.Twitter.service;

import by.application.Twitter.model.Like;
import by.application.Twitter.model.User;
import by.application.Twitter.repository.LikeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class LikeServiceImplIntegrationTest {
    @TestConfiguration
    static class LikeServiceImplTestContextConfiguration {

        @Bean
        public LikeService likeService() {
            return new LikeServiceImpl();
        }
    }

    @Autowired
    private LikeService likeService;
    @MockBean
    private LikeRepository likeRepository;
    @MockBean
    private UserService userService;

    private Like like1User1Post1, like2User1Post2, like3User2Post1;
    private User user1, user2;
    private List<Like> allLikesUser1, allLikesPost1;
    private List<User> allUsers;
    private Pageable pageable;
    private Page<Like> likePage;

    @Before
    public void setUp() {
        user1 = new User(1, "gormak", "123", "Yahor", "Makarchuk", "kifew55498@ximtyl.com", null);
        user2 = new User(2, "gormak2", "1234", "Yahor2", "Makarchuk2", "kifew55498@ximtyl.com", null);
        like1User1Post1 = new Like(1, 1, 1);
        like2User1Post2 = new Like(2, 2, 1);
        like3User2Post1 = new Like(3, 1, 2);

        pageable = PageRequest.of(0, 2);

        allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);

        allLikesUser1 = new ArrayList<>();
        allLikesUser1.add(like1User1Post1);
        allLikesUser1.add(like2User1Post2);

        allLikesPost1 = new ArrayList<>();
        allLikesPost1.add(like1User1Post1);
        allLikesPost1.add(like3User2Post1);

        likePage = new PageImpl<Like>(allLikesUser1);

        Mockito.when(likeRepository.findByPostIdAndUserId(2, 1))
                .thenReturn(null);
        Mockito.when(likeRepository.count())
                .thenReturn((long) 1);
        Mockito.when(likeRepository.save(like2User1Post2))
                .thenReturn(like2User1Post2);
        Mockito.when(likeRepository.findByPostIdAndUserId(1, 1))
                .thenReturn(like1User1Post1);
        Mockito.when(likeRepository.findAllByUserId(1, pageable))
                .thenReturn(likePage);
        Mockito.when(likeRepository.findAllByPostId(1))
                .thenReturn(allLikesPost1);
        Mockito.when(userService.getUserById(1))
                .thenReturn(user1);
        Mockito.when(userService.getUserById(2))
                .thenReturn(user2);
        Mockito.doNothing().when(likeRepository).deleteById(like1User1Post1.getId());

    }

    @Test
    public void createLike() {
        //when
        boolean status = likeService.createLike(2, 1);

        // then
        Assertions.assertThat(status)
                .isEqualTo(true);
    }

    @Test
    public void deleteLike() {
        //when
        boolean status = likeService.deleteLike(1, 1);

        // then
        Assertions.assertThat(status)
                .isEqualTo(true);
    }

    @Test
    public void getAllLikesByUserId() {
        //when
        Page<Like> likePageResult = likeService.getAllLikesByUserId(user1.getId(), pageable);

        // then
        Assertions.assertThat(likePageResult)
                .isEqualTo(likePage);
    }

    @Test
    public void getAllUsersByLikeOnPost() {
        //when
        List<User> userListResult = likeService.getAllUsersByLikeOnPost(1);

        // then
        Assertions.assertThat(userListResult)
                .isEqualTo(allUsers);
    }
}
