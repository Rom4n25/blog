package pl.romanek.blog.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;

    private LocalDateTime created;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OrderBy("created")
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private Set<Comment> comment;
}
