package pl.romanek.blog.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private String text;
    private MultipartFile file;
    private Integer postId;
}
