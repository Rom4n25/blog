package pl.romanek.blog.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import pl.romanek.blog.dto.CommentDto;
import pl.romanek.blog.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toCommentDto(Comment Comment);

    Comment toCommentEntity(CommentDto CommentDto);

    List<CommentDto> toCommentsDto(List<Comment> Comments);

    List<Comment> toCommentsEntity(List<CommentDto> Comments);
}
