package by.application.Twitter.repository;

import by.application.Twitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    User save(User user);

    //User findById(int id);

    User findByUsername(String username);

    //UPDATE
    @Transactional
    @Modifying
    @Query(value = "UPDATE users " +
            "SET username = ?1, " +
            "password = ?2, " +
            "first_name = ?3, " +
            "last_name = ?4, " +
            "email = ?5 " +
            "WHERE id = ?6",
            nativeQuery = true)
    int updateUser(String username, String password, String firstName, String secondName, String email, int id);

    User findByActivationCode(String code);
}
