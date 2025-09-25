package xyz.catuns.spring.base.mapper;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import xyz.catuns.spring.base.dto.PageList;

/**
 * Child interfaces should be annotated {@link Mapper}
 * @param <T> the Base entity
 * @param <D> the entity details object
 * @param <E> the entity edit object
 */
public interface EntityMapper<T, D, E> {

    @Mapping(target = "page", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize")
    @Mapping(target = "items", source = "content", defaultExpression = "java(new ArrayList<>())")
    PageList<D> toPageList(Page<T> page);

    D toDetails(T entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget T entity, E edit);

}
