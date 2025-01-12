package toy.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import toy.board.domain.dto.request.UserSignupRequest;
import toy.board.domain.dto.response.SignupSuccessResponse;
import toy.board.domain.dto.response.UserInfoResponse;
import toy.board.domain.entity.UserEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "password", source = "encryptedPassword")
    UserEntity signupRequestToEntity(UserSignupRequest request, String encryptedPassword);

    SignupSuccessResponse entityToSignupResponse(UserEntity entity);

    UserInfoResponse entityToInfoResponse(UserEntity entity);

}
