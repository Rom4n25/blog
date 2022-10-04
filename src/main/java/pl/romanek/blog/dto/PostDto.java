package pl.romanek.blog.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.entity.User;

@Getter
@Setter
public class PostDto {

    private int id;
    private String text;
    private User user;
    private Set<Comment> comment;
}
