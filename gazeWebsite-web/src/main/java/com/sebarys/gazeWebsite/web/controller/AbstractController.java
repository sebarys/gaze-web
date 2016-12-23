package com.sebarys.gazeWebsite.web.controller;

import com.sebarys.gazeWebsite.service.AbstractService;
import com.sebarys.gazeWebsite.service.mappers.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

abstract public class AbstractController<DBO, DTO, S extends AbstractService<DBO, DTO, ? extends CrudRepository<DBO, Long>, ? extends MapperInterface<DBO, DTO>>> {

    @Autowired
    S service;

    //@RequestMapping("/{id}")
    public DTO findOne(@PathVariable int id, HttpServletRequest request) {
        return service.findOne(new Long(id));
    }

   // @RequestMapping("/all")
    public Iterable<DTO> findAll() {
        return service.findAll();
    }

    //@RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody DTO modelToSave) {
        service.save(modelToSave);
    }

    //@RequestMapping(value = "/save/all", method = RequestMethod.POST)
    public void save(@RequestBody Iterable<DTO> modelsToSave) {
        service.save(modelsToSave);
    }

    //@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(@RequestBody DTO modelToDelete, @PathVariable int id) {
        service.delete(modelToDelete);
    }

    //@RequestMapping(value = "/delete/all", method = RequestMethod.POST)
    public void delete(@RequestBody Iterable<DTO> modelsToDelete) {
        service.delete(modelsToDelete);
    }
}
