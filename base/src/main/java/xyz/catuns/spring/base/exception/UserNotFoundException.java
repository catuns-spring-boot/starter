package xyz.catuns.spring.base.exception;

import xyz.catuns.spring.base.exception.controller.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found", id));
    }
}
