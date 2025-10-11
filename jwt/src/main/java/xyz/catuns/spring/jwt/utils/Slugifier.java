package xyz.catuns.spring.jwt.utils;

import com.github.slugify.Slugify;

public interface Slugifier {

    Slugify INSTANCE = new Slugify();

    String slugify(String name);
}
