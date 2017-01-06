package com.sebarys.gazeWebsite.web.controller;

import com.sebarys.gazeWebsite.service.AbstractService;
import com.sebarys.gazeWebsite.service.mappers.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

abstract public class AbstractController<DBO, DTO, S extends AbstractService<DBO, DTO, ? extends CrudRepository<DBO, Long>, ? extends MapperInterface<DBO, DTO>>> {

    @Autowired
    S service;

    public DTO findOne(@PathVariable int id) {
        return service.findOne((long) id);
    }

    public Iterable<DTO> findAll() {
        return service.findAll();
    }

    public void save(@RequestBody DTO modelToSave) {
        service.save(modelToSave);
    }

    public void save(@RequestBody Iterable<DTO> modelsToSave) {
        service.save(modelsToSave);
    }

    public void delete(@RequestBody DTO modelToDelete) {
        service.delete(modelToDelete);
    }

    public void delete(@RequestBody Iterable<DTO> modelsToDelete) {
        service.delete(modelsToDelete);
    }
}
