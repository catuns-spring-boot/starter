package xyz.catuns.spring.base.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.exception.controller.NotFoundException;
import xyz.catuns.spring.base.mapper.EntityMapper;

import java.util.List;

public abstract class JpaCrudService<Entity, ID, EntityDetails> implements CrudService<ID, EntityDetails> {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public PageList<EntityDetails> getAll(PageRequest pageRequest) {
        Page<Entity> all = getRepository().findAll(pageRequest);
        return getMapper().toPageList(all);
    }

    @Override
    public List<EntityDetails> getAll() {
        List<Entity> all = getRepository().findAll();
        return all.stream()
                .map(getMapper()::toDetails)
                .toList();
    }

    public EntityDetails getOne(ID id) {
        Entity entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("no entity for id " + id));
        return getMapper().toDetails(entity);
    }

    @SuppressWarnings("unchecked")
    public <E> EntityDetails edit(ID id, E edit) {
        Entity entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("no entity for id " + id));
        ((EntityMapper<Entity,EntityDetails, E>) getMapper()).update(entity, edit);
        entity = getRepository().save(entity);
        return getMapper().toDetails(entity);
    }

    public void delete(ID entityId) {
        getRepository().deleteById(entityId);
    }

    protected abstract EntityMapper<Entity,EntityDetails,?> getMapper();

    protected abstract JpaRepository<Entity, ID> getRepository();
}
