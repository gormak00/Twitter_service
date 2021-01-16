package by.application.Twitter.repository;

import by.application.Twitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    User save(User user);

    User findById(int id);

    User findByUsername(String username);
}
