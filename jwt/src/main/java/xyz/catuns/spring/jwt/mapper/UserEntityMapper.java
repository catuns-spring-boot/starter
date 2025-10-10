package xyz.catuns.spring.jwt.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import xyz.catuns.spring.jwt.dto.UserLoginResponse;
import xyz.catuns.spring.jwt.dto.UserRegister;
import xyz.catuns.spring.jwt.dto.UserResponse;
import xyz.catuns.spring.jwt.model.UserEntity;
import xyz.catuns.spring.jwt.model.UserRole;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserEntityMapper extends AuthenticationServiceMapper<UserEntity,UserResponse,UserLoginResponse, UserRegister> {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserResponse toRegisterResponse(UserEntity user);
    UserLoginResponse toLoginResponse(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity map(UserRegister registration);

    default String roleToString(UserRole role) {
        return role != null ? role.toString() : "";
    }
}
