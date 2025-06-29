package xyz.catuns.spring.base;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.service.CrudService;

public abstract class CrudController<Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO> {

    protected final CrudService<Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO> service;

    protected CrudController(CrudService<Entity, PrimaryKey, EntityDTO, CreationDTO, EditDTO> service) {
        this.service = service;
    }


    @GetMapping("")
    @Operation(
            summary = "Get All entities",
            description = "REST API to fetch entities")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<PageList<EntityDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        PageList<EntityDTO> pagelist = service.getAll(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(pagelist);
    }

    @PostMapping("")
    @Operation(
            summary = "Create ",
            description = "REST API to create entity")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED")
    public ResponseEntity<EntityDTO> createCrud(
            @Valid @RequestBody CreationDTO crudCreation
    ){
        EntityDTO entity = service.create(crudCreation);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Edit Crud",
            description = "REST API to edit existing entity")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<EntityDTO> editCrud(
            @PathVariable("id") PrimaryKey id,
            @RequestBody EditDTO crudEdit
    ){
        EntityDTO entity = service.edit(id, crudEdit);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Disable",
            description = "REST API to delete entity")
    @ApiResponse(
            responseCode = "204",
            description = "HTTP Status NO CONTENT")
    public ResponseEntity<Void> delete(
            @PathVariable("id") PrimaryKey id
    ){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
