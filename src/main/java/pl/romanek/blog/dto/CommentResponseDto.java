package pl.romanek.blog.dto;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import pl.romanek.blog.entity.PointComment;

@Getter
@Setter
public class CommentResponseDto {
    private int id;
    private String text;
    private Set<PointComment> pointComment;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private UserResponseDto user;
    private byte[] img;
}
