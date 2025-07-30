package xyz.catuns.spring.base.service;

import org.springframework.data.domain.PageRequest;
import xyz.catuns.spring.base.dto.PageList;

import java.util.List;

public interface CrudService <Identifier, DTO> {

    PageList<DTO> getAll(PageRequest pageRequest);

    List<DTO> getAll();

    DTO getOne(Identifier id);

    <C> DTO create(C creation);

    <E> DTO edit(Identifier id, E edit);

    void delete(Identifier id);

}
