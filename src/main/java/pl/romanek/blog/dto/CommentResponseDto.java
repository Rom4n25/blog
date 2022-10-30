package pl.romanek.blog.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private int id;
    private String text;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private UserResponseDto user;
}
