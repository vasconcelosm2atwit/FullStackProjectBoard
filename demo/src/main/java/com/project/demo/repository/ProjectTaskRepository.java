package com.project.demo.repository;

import com.project.demo.domain.ProjectTask;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long>{
    ProjectTask getById(Long id);

}
