package by.application.Twitter.repository;

import by.application.Twitter.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Like save(Like like);

    Like findByPostIdAndUserId(int postId, int userId);

    void deleteById(int id);

    List<Like> findAllByPostId(int postId);

    Page<Like> findAllByUserId(int userId, Pageable pageable);
}
