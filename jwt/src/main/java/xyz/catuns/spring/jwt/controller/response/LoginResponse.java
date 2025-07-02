package xyz.catuns.spring.jwt.controller.response;

import java.io.Serializable;
import java.util.Date;

public interface LoginResponse extends Serializable {
    String token();
    Date expiration();
}
