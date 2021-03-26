package com.project.demo.service;

import com.project.demo.domain.ProjectTask;
import com.project.demo.repository.ProjectTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask saveOrUpdateProjectTask(ProjectTask projectTask){
        // if status is null or empty we create a status
        if(projectTask.getStatus() == null || projectTask.getStatus() == ""){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask); // save is a repository function from spring 
    }

    public Iterable<ProjectTask> findAll(){
        return projectTaskRepository.findAll();
    }

    public ProjectTask findById(long id){
        return projectTaskRepository.getById(id);
    }

    public void delete(long id){
        ProjectTask projectTask = findById(id);
        projectTaskRepository.delete(projectTask);
    }

    
}
