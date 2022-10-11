package pl.romanek.blog.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private int id;
    private String text;
    private UserResponseDto user;
    private Set<CommentDto> comment;
}
