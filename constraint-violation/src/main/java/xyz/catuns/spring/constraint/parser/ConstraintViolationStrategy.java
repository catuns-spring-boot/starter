package xyz.catuns.spring.constraint.parser;

public interface ConstraintViolationStrategy {
    boolean matches(String message);
    ConstraintViolationInfo parse(String message);
}