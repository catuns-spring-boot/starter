package xyz.catuns.spring.base.config.user;

import xyz.catuns.spring.base.controller.request.UserRegistration;
import xyz.catuns.spring.base.dto.UserResponse;
import xyz.catuns.spring.base.service.UserEntityService;

public class AdminUserEntity {

    private final UserEntityService userService;
    private final UserProperties userProperties;

    public AdminUserEntity(UserEntityService userService, UserProperties userProperties) {
        this.userService = userService;
        this.userProperties = userProperties;
    }

    public UserResponse create() {
        UserRegistration registration = new UserRegistration(

        );
        return userService.registerAdmin(registration);
    }

}
