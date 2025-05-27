package xyz.catuns.spring.jwt.config.user;

import xyz.catuns.spring.jwt.service.UserEntityService;

public class AdminUserEntity {

    private final UserEntityService userService;
    private final AdminUserProperties adminUserProperties;

    public AdminUserEntity(UserEntityService userService, AdminUserProperties adminUserProperties) {
        this.userService = userService;
        this.adminUserProperties = adminUserProperties;
    }

}
