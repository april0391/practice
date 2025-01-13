package toy.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import toy.board.domain.dto.post.PostDto;
import toy.board.domain.entity.PostEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.nickname", target = "writer")
    })
    PostDto entityToResponse(PostEntity postEntity);

}
