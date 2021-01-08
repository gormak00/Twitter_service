package by.application.Twitter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "user_id")
    private int userId;
}
