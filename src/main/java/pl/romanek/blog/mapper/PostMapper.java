package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import pl.romanek.blog.dto.PostDto;
import pl.romanek.blog.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toPostDto(Post Post);

    Post toPostEntity(PostDto PostDto);

    List<PostDto> toPostsDto(List<Post> Posts);

    List<Post> toPostsEntity(List<PostDto> Posts);
}
