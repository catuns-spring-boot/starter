package xyz.catuns.spring.jwt.utils;

import com.github.slugify.Slugify;

@FunctionalInterface
public interface Slugifier {

    static String slug(String text) {
        return new Slugify().slugify(text);
    }

    String slugify(String name);
}
