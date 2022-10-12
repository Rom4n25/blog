package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import pl.romanek.blog.dto.CommentResponseDto;
import pl.romanek.blog.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentResponseMapper {

    CommentResponseDto toCommentResponseDto(Comment comment);

    List<CommentResponseDto> toCommentsResponseDto(List<Comment> comments);

}
