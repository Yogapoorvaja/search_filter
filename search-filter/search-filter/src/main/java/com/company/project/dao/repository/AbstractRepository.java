package com.company.project.dao.repository;

import com.company.project.dao.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
}
