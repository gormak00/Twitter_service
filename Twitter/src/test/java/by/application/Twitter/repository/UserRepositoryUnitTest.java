package by.application.Twitter.repository;

import by.application.Twitter.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class UserRepositoryUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void beforeAll() {
        user = new User(1, "Yahor", "Makarchuk", "gormak", "kifew55498@ximtyl.com", "123", null);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void getUserByUsername() {
        // when
        User userFromRepo = userRepository.findByUsername(user.getUsername());

        // then
        Assertions.assertThat(userFromRepo.getUsername())
                .isEqualTo(user.getUsername());
    }

    @Test
    public void getUserById() {
        // when
        User userFromRepo = userRepository.findById(1).get();

        // then
        Assertions.assertThat(userFromRepo.getUsername())
                .isEqualTo(user.getUsername());
    }

    @Test
    public void updateUserById() {
        // when
        int count = userRepository.updateUser("newUsername", "123", "Yahor", "Makarchuk", "kifew55498@ximtyl.com", 1);

        // then
        Assertions.assertThat(count)
                .isEqualTo(1);
    }
}
