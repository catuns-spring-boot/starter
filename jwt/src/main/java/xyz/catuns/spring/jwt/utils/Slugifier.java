package xyz.catuns.spring.jwt.utils;

@FunctionalInterface
public interface Slugifier {

    String slugify(String name);
}
