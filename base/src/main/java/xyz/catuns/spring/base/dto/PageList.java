package xyz.catuns.spring.base.dto;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public record PageList<Entity>(
        int page,
        int pageSize,
        int totalPages,
        List<Entity> items
) {

    public static <Entity> PageList<Entity> map(Page<Entity> page) {
        return new PageList<>(
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getTotalPages(), page.getContent()
        );
    }

    public static <Entity> PageList<Entity> map(List<Entity> entities) {
        return new PageList<>(0, entities.size(), 1, entities);
    }
}