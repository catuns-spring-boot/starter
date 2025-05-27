package xyz.catuns.spring.base.config.user;

import xyz.catuns.spring.base.service.UserEntityService;

public class AdminUserEntity {

    private final UserEntityService userService;
    private final AdminUserProperties adminUserProperties;

    public AdminUserEntity(UserEntityService userService, AdminUserProperties adminUserProperties) {
        this.userService = userService;
        this.adminUserProperties = adminUserProperties;
    }

}
