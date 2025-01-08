package toy.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import toy.board.domain.dto.UserSignupRequest;
import toy.board.domain.dto.UserSignupResponse;
import toy.board.domain.entity.UserEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "password", source = "encryptedPassword")
    UserEntity signupRequestToEntity(UserSignupRequest request, String encryptedPassword);

    UserSignupResponse entityToSignupResponse(UserEntity entity);

}
