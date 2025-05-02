package xyz.catuns.spring.base.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.catuns.spring.base.dto.UserResponse;
import xyz.catuns.spring.base.model.user.UserEntity;

@Mapper
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserResponse toResponse(UserEntity user);
}
