package pl.romanek.blog.dto;

import lombok.Getter;
import lombok.Setter;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;

@Getter
@Setter
public class CommentDto {

    private int id;
    private String text;
    private Post post;
    private User user;
}
