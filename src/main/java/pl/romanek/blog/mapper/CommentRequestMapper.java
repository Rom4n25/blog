package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pl.romanek.blog.dto.CommentRequestDto;
import pl.romanek.blog.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toCommentEntity(CommentRequestDto commentRequestDto);

    List<Comment> toCommentsEntity(List<CommentRequestDto> commentsRequestDto);
}