package xyz.catuns.spring.base.config.user;

import xyz.catuns.spring.base.service.UserEntityService;

public class AdminUserEntity {

    private final UserEntityService userService;
    private final UserProperties userProperties;

    public AdminUserEntity(UserEntityService userService, UserProperties userProperties) {
        this.userService = userService;
        this.userProperties = userProperties;
    }

}
