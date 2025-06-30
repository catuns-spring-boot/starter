package xyz.catuns.spring.base.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.mapper.EntityMapper;

public abstract class JpaCrudService<Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO>
        implements CrudService<Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO>
{

    protected final EntityMapper<Entity, EntityDTO, CreationDTO, EditDTO> mapper;
    protected final JpaRepository<Entity, PrimaryKey> repository;

    public JpaCrudService(JpaRepository<Entity, PrimaryKey> repository, EntityMapper<Entity, EntityDTO, CreationDTO, EditDTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PageList<EntityDTO> getAll(PageRequest pageRequest) {
        Page<Entity> all = repository.findAll(pageRequest);
        return mapper.toPageList(all);
    }

    public EntityDTO getOne(PrimaryKey id) {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity id " + id));
        return mapper.toDetails(entity);
    }

    public EntityDTO create(CreationDTO creationDTO) {
        Entity entity = mapper.map(creationDTO);
        entity = repository.save(entity);
        return mapper.toDetails(entity);
    }

    public EntityDTO edit(PrimaryKey id, EditDTO edit) {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity id " + id));
        mapper.update(entity, edit);
        entity = repository.save(entity);
        return mapper.toDetails(entity);
    }

    public void delete(PrimaryKey entityId) {
        repository.deleteById(entityId);
    }
}
