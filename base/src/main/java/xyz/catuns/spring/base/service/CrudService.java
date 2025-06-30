package xyz.catuns.spring.base.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.mapper.EntityMapper;

public interface CrudService <Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO> {

    PageList<EntityDTO> getAll(PageRequest pageRequest);
    EntityDTO getOne(PrimaryKey id);
    EntityDTO create(CreationDTO creationDTO);
    EntityDTO edit(PrimaryKey id, EditDTO edit);
    void delete(PrimaryKey entityId);
}
