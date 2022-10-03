package pl.romanek.blog.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {

    @Id
    private int id;
    private String text;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private Set<Comment> comment;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public Set<Comment> getComment() {
        return this.comment;
    }
}
