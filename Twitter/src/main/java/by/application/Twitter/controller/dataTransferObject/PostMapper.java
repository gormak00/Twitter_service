package by.application.Twitter.controller.dataTransferObject;

import by.application.Twitter.model.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    static Post ToPost(PostDto postDto, int userId) {
        Post post = new Post();
        post.setUserId(userId);
        post.setData(postDto.getData());
        return post;
    }
}
