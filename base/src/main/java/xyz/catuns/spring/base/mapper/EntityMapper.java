package xyz.catuns.spring.base.mapper;


import org.mapstruct.*;
import org.springframework.data.domain.Page;
import xyz.catuns.spring.base.dto.PageList;

import java.util.Collection;
import java.util.List;

/**
 * Child interfaces should be annotated {@link Mapper}
 * @param <T> the Base entity
 * @param <D> the entity details object
 */
public interface EntityMapper<T, D> {

    @Mapping(target = "page", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize")
    @Mapping(target = "items", source = "content", defaultExpression = "java(new ArrayList<>())")
    PageList<D> toPageList(Page<T> page);

    @Mapping(target = "id", ignore = true)
    <C> T map(C creation);

    D toDetails(T entity);

    List<D> toDetails(List<T> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    <E> void update(@MappingTarget T entity, E edit);
}
