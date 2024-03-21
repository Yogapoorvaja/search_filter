package com.company.project.service;

import com.company.project.dao.model.AbstractEntity;
import com.company.project.dao.repository.AbstractRepository;
import com.company.project.service.common.searchable.SearchableService;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

public class AbstractService<T extends AbstractEntity> extends SearchableService<T> {
    protected final AbstractRepository<T> repository;

    public AbstractService(AbstractRepository<T> repository) {
        super(repository);
        this.repository = repository;
    }

    public T findById(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + " not found"));
    }

    @Transactional
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
