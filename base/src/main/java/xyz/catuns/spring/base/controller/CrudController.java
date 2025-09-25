package xyz.catuns.spring.base.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.catuns.spring.base.dto.PageList;
import xyz.catuns.spring.base.service.CrudService;

public abstract class CrudController<Identifier, DTO> {

    protected abstract CrudService<Identifier, DTO> getService();


    @GetMapping("")
    @Operation(
            summary = "Get All entities",
            description = "REST API to fetch entities")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<PageList<DTO>> getAllPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        PageList<DTO> pagelist = getService().getAll(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(pagelist);
    }

    @PostMapping("")
    @Operation(
            summary = "Create ",
            description = "REST API to create entity")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED")
    public ResponseEntity<DTO> createCrud(
            @Valid @RequestBody Object crudCreation
    ) {
        DTO entity = getService().create(crudCreation);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Edit Crud",
            description = "REST API to edit existing entity")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public <E> ResponseEntity<DTO> editCrud(
            @PathVariable("id") Identifier id,
            @RequestBody E crudEdit
    ){
        DTO entity = getService().edit(id, crudEdit);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get by identifier",
            description = "REST API to entity by identifier")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<DTO> getOne(
            @PathVariable("id") Identifier id
    ){
        DTO entity = getService().getOne(id);
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
            @PathVariable("id") Identifier id
    ){
        getService().delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
