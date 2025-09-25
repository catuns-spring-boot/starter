package xyz.catuns.spring.base.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class PageList<Entity> {
    protected int page;
    protected int pageSize;
    protected int totalPages;
    protected List<Entity> items;
}