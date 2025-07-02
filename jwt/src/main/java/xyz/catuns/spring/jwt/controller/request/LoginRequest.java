package xyz.catuns.spring.jwt.controller.request;

import java.io.Serializable;

public interface LoginRequest extends Serializable {
    String username();
    String password();
}
