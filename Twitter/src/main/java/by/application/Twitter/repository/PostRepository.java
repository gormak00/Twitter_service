package by.application.Twitter.repository;

import by.application.Twitter.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post save(Post post);

    Page<Post> findAll(@Nullable Pageable pageable);

    Post findById(int id);

    void deleteById(int id);

    Post findByIdAndUserId(int id, int userId);

    //UPDATE
    @Transactional
    @Modifying
    @Query(value = "UPDATE posts " +
            "SET data = ?1 " +
            "WHERE id = ?2",
            nativeQuery = true)
    int updatePostById(String data, int postId);
}
