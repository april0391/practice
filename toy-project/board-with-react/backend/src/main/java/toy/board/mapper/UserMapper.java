package toy.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import toy.board.domain.dto.user.SignupRequest;
import toy.board.domain.dto.user.UserDetailInfoResponse;
import toy.board.domain.dto.user.UserSimpleInfoResponse;
import toy.board.domain.entity.UserEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "password", source = "encryptedPassword")
    UserEntity signupRequestToEntity(SignupRequest request, String encryptedPassword);

    UserSimpleInfoResponse entityToSimpleInfoResponse(UserEntity entity);

    UserDetailInfoResponse entityToInfoResponse(UserEntity entity);

}
