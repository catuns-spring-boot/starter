package xyz.catuns.spring.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.exception.NotFoundException;
import xyz.catuns.spring.base.mapper.EntityMapper;

public abstract class JpaCrudService<Entity, ID, EntityDetails> implements CrudService<ID, EntityDetails> {

    protected static EntityMapper<?,?,?,?> mapper;
    protected final JpaRepository<Entity, ID> repository;

    public JpaCrudService(JpaRepository<Entity, ID> repository) {
        this.repository = repository;
    }

//    public PageList<EntityDetails> getAll(PageRequest pageRequest) {
//        Page<Entity> all = repository.findAll(pageRequest);
//        return mapper.toPageList(all);
//    }
//
//    public EntityDetails getOne(ID id) {
//        Entity entity = repository.findById(id)
//                .orElseThrow(() -> new NotFoundException("no entity for id " + id));
//        return mapper.toDetails(entity);
//    }
//
//    public EntityDetails create(C creationDTO) {
//        Entity entity = mapper.map(creationDTO);
//        entity = repository.save(entity);
//        return mapper.toDetails(entity);
//    }
//
//    public EntityDetails edit(ID id, E edit) {
//        Entity entity = repository.findById(id)
//                .orElseThrow(() -> new NotFoundException("no entity for id " + id));
//        mapper.update(entity, edit);
//        entity = repository.save(entity);
//        return mapper.toDetails(entity);
//    }
//
//    public void delete(ID entityId) {
//        repository.deleteById(entityId);
//    }


}
