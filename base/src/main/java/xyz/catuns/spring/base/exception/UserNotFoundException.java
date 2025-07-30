package xyz.catuns.spring.base.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found", id));
    }
}
