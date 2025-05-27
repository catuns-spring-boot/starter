package xyz.catuns.spring.jwt.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import xyz.catuns.spring.jwt.controller.request.UserRegistration;
import xyz.catuns.spring.jwt.controller.request.UserUpdate;
import xyz.catuns.spring.jwt.dto.UserResponse;
import xyz.catuns.spring.jwt.model.UserEntity;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserResponse toResponse(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity map(UserRegistration registration);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserUpdate userUpdate, @MappingTarget UserEntity user);
}
