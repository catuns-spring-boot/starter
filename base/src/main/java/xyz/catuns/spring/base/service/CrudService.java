package xyz.catuns.spring.base.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.mapper.EntityMapper;

public abstract class CrudService <Entity, PrimaryKey, Details, Creation, Editor> {

    protected final EntityMapper<Entity, Details, Creation, Editor> mapper;
    protected final JpaRepository<Entity, PrimaryKey> repository;

    public CrudService(JpaRepository<Entity, PrimaryKey> repository, EntityMapper<Entity, Details, Creation, Editor> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PageList<Details> getAll(PageRequest pageRequest) {
        Page<Entity> all = repository.findAll(pageRequest);
        return mapper.toPageList(all);
    }

    public Details getOne(PrimaryKey id) {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity id " + id));
        return mapper.toDetails(entity);
    }

    public Details create(Creation creation) {
        Entity entity = mapper.map(creation);
        entity = repository.save(entity);
        return mapper.toDetails(entity);
    }

    public Details edit(PrimaryKey id, Editor edit) {
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
