package xyz.catuns.spring.base.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.catuns.spring.base.dto.PageList;

public interface CrudService <Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO> {

    PageList<EntityDTO> getAll(PageRequest pageRequest);
    EntityDTO getOne(PrimaryKey id);
    EntityDTO create(CreationDTO creationDTO);
    EntityDTO edit(PrimaryKey id, EditDTO edit);
    void delete(PrimaryKey entityId);
    default String authenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }
}
