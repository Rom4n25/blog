package pl.romanek.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.romanek.blog.dto.CommentRequestDto;
import pl.romanek.blog.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    Comment toCommentEntity(CommentRequestDto commentRequestDto);
}