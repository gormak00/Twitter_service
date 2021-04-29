package by.application.Twitter.service;

import by.application.Twitter.config.security.JWTUtil;
import by.application.Twitter.model.LoginDetails;
import by.application.Twitter.model.User;
import by.application.Twitter.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceImplUnitTest {
    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MailSender mailSender;
    @MockBean
    private LikeService likeService;
    @MockBean
    private PostService postService;
    @MockBean
    private JWTUtil jwtTokenUtil;

    private User user, user2, user3;
    private List<User> allUsers;
    private String activationCode, token;
    private LoginDetails loginDetails;

    @BeforeEach
    public void setUp() {
        activationCode = "Activation code";
        token = "token";
        user = new User(1, "Yahor", "Makarchuk", "gormak", "kifew55498@ximtyl.com", "1232", null);
        user2 = new User(2, "Yahor2", "Makarchuk2", "gormak2", "kifew55498@ximtyl.com", "1232", null);
        user3 = new User(3, "Yahor3", "Makarchuk3", "gormak3", "kifew55498@ximtyl.com", "1232", activationCode);
        allUsers = new ArrayList<>();
        allUsers.add(user);
        allUsers.add(user2);
        loginDetails = new LoginDetails(user3.getUsername(), user3.getPassword());
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findAll())
                .thenReturn(allUsers);
        Mockito.when(userRepository.save(user3))
                .thenReturn(user3);
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(optionalUser);
        Mockito.when(userService.getUserByUsername(user2.getUsername()))
                .thenReturn(user2);
        Mockito.when(userRepository.updateUser(user3.getUsername(), user3.getPassword(), user3.getFirstName(), user3.getLastName(), user3.getEmail(), 2))
                .thenReturn(1);
        Mockito.when(userRepository.findByActivationCode(activationCode))
                .thenReturn(user3);
        Mockito.when(userService.getUserByUsername("gormak"))
                .thenReturn(user);
        Mockito.when(jwtTokenUtil.generateToken(loginDetails))
                .thenReturn("token");
        Mockito.doNothing().when(userRepository).deleteById(2);
    }

    @Test
    public void createUser() {
        //when
        boolean userWasAdded = userService.createUser(user3);

        // then
        Assertions.assertThat(userWasAdded)
                .isTrue();
    }

    @Test
    public void existUserByCredentials() {
        //when
        boolean userIsExist = userService.existUserByCredentials(new LoginDetails(user.getUsername(), user.getPassword()));

        // then
        Assertions.assertThat(userIsExist)
                .isTrue();
    }

    @Test
    public void allUsers() {
        //when
        List<User> allUsersFromService = userService.getAllUsers();

        // then
        Assertions.assertThat(allUsers.size())
                .isEqualTo(2);
    }

    @Test
    public void getUserById() {
        //when
        User userFromService = userService.getUserById(user.getId());

        // then
        Assertions.assertThat(userFromService.getUsername())
                .isEqualTo(user.getUsername());
    }

    @Test
    public void getUserByUsername() {
        //when
        User userFromService = userService.getUserByUsername(user2.getUsername());

        // then
        Assertions.assertThat(userFromService.getId())
                .isEqualTo(user2.getId());
    }

    @Test
    public void updateUser() {
        //when
        userService.updateUser(user3, 2);

        // then
        Assertions.assertThat(userRepository.updateUser(user3.getUsername(), user3.getPassword(), user3.getFirstName(), user3.getLastName(), user3.getEmail(), 2))
                .isEqualTo(1);
    }

    @Test
    public void deleteUserById() {
        //when
        boolean status = userService.deleteUserById(2);

        // then
        Assertions.assertThat(status)
                .isEqualTo(true);
    }

    @Test
    public void activateUser() {
        //when
        String token = userService.activateUser(activationCode);

        // then
        Assertions.assertThat(userRepository.findByActivationCode(activationCode))
                .isEqualTo(user3);
    }

    @Test
    public void isUserActivated() {
        String username = "gormak";
        //when
        boolean status = userService.isUserActivated(username);

        // then
        Assertions.assertThat(status)
                .isTrue();
    }
}
