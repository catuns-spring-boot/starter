package xyz.catuns.spring.jwt.mapper;

import xyz.catuns.spring.jwt.controller.request.RegisterRequest;
import xyz.catuns.spring.jwt.controller.response.LoginResponse;
import xyz.catuns.spring.jwt.controller.response.RegisterResponse;

public interface AuthenticationMapper<
        UserEntity,
        RegResp extends RegisterResponse,
        LogResp extends LoginResponse,
        RegReq extends RegisterRequest
> {
    RegResp toRegisterResponse(UserEntity user);

    LogResp toLoginResponse(UserEntity user);

    UserEntity map(RegReq registration);
}
