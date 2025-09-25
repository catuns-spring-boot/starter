package xyz.catuns.spring.base.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableRequestParams {
    protected int page = 0;
    protected int pageSize = 5;
}
