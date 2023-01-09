package pl.romanek.blog.dto;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import pl.romanek.blog.entity.PointPost;
import pl.romanek.blog.entity.Tag;

@Getter
@Setter
public class PostResponseDto {

    private int id;
    private String text;
    private Set<PointPost> pointPost;
    private Set<Tag> tag;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private UserResponseDto user;
    private Set<CommentResponseDto> comment;
    private byte[] img;
}
