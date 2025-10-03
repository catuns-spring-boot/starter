package xyz.catuns.spring.base.controller.request;

public record PageableRequestParams(
        Integer page,
        Integer size
) {
    public PageableRequestParams {
        if (page == null) page = 0;
        if (size == null) size = 10;
    }
}