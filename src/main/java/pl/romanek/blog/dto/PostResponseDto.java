package pl.romanek.blog.dto;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

    private int id;
    private String text;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private UserResponseDto user;
    private Set<CommentResponseDto> comment;
    private byte[] img;
}
