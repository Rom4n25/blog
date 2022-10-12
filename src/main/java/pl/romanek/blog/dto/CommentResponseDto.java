package pl.romanek.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private int id;
    private String text;
    private UserResponseDto user;
}
