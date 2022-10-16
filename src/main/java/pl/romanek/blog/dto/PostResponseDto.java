package pl.romanek.blog.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

    private int id;
    private String text;
    private UserResponseDto user;
    private Set<CommentResponseDto> comment;
}
