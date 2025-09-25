package xyz.catuns.spring.base.mapper.annotations;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "page", source = "pageable.pageNumber")
@Mapping(target = "pageSize", source = "pageable.pageSize")
@Mapping(target = "items", source = "content", defaultExpression = "java(new ArrayList<>())")
public @interface ToPageList {
}
