package xyz.catuns.spring.base.mapper;


import org.mapstruct.*;
import org.springframework.data.domain.Page;
import xyz.catuns.spring.base.dto.PageList;

import java.util.Collection;

/**
 * Child interfaces should be annotated {@link Mapper}
 * @param <T> the Base entity
 * @param <D> the entity details object
 * @param <C> the Creation object
 * @param <E> the Editor object
 */
public interface EntityMapper<T, D, C, E> {

    @Mapping(target = "page", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize")
    @Mapping(target = "items", source = "content", defaultExpression = "java(new ArrayList<>())")
    PageList<D> toPageList(Page<T> page);

    @Mapping(target = "id", ignore = true)
    T map(C creation);

    D toDetails(T entity);

    Collection<D> toDetails(Collection<T> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget T entity, E edit);
}
