package com.sebarys.gazeWebsite.service;

import com.sebarys.gazeWebsite.service.mappers.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

/**
 * Abstract service with four generic type
 *
 * @DBO database object
 * @DTO data transfer object
 * @R repository extends CrudRepository
 * @M mapper implements MapperInterface
 */
public abstract class AbstractService<DBO, DTO, R extends CrudRepository<DBO, Long>, M extends MapperInterface<DBO, DTO>> {

    @Autowired
    R repo;

    @Autowired
    M mapper;

    public DTO findOne(Long id) {
        DBO dbo = repo.findOne(id);
        if(dbo != null) {
            return mapper.convertToDTO(dbo);
        }
        return null;
    }

    public Iterable<DTO> findAll() {
        return mapper.convertToDTO(repo.findAll());
    }

    public DBO save(DTO modelToSave) {
        return repo.save(mapper.convertToDBO(modelToSave));
    }

    public void save(Iterable<DTO> modelsToSave) {
        repo.save(mapper.convertToDBO(modelsToSave));
    }

    public void delete(DTO modelToDelete) {
        repo.delete(mapper.convertToDBO(modelToDelete));
    }

    public void delete(Iterable<DTO> modelsToDelete) {
        repo.delete(mapper.convertToDBO(modelsToDelete));
    }

    public M getMapper() {
        return mapper;
    }
}
