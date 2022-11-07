package pl.romanek.blog.mapper;

import java.io.IOException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.romanek.blog.dto.CommentRequestDto;
import pl.romanek.blog.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    @Mapping(source = "commentRequestDto", target = "img", qualifiedByName = "img")
    Comment toCommentEntity(CommentRequestDto commentRequestDto);

    @Named("img")
    default byte[] img(CommentRequestDto commentRequestDto) throws IOException {
        return commentRequestDto.getFile() == null ? null : commentRequestDto.getFile().getBytes();
    }
}