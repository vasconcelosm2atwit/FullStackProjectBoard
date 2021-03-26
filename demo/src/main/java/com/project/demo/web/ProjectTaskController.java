package com.project.demo.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.project.demo.domain.ProjectTask;
import com.project.demo.service.ProjectTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@CrossOrigin // this allows our api to be accessed by other services without this we get a foward error
public class ProjectTaskController {
    
    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("") // @valid gives us the actual error message instead of a internal 500 error
    public ResponseEntity<?> addPTToBoard(@Valid @RequestBody ProjectTask projectTask, BindingResult result){
        // bindingResult is from the validation import, 
        // HANDLING THE ERROR
        if(result.hasErrors()){
            Map<String,String> errorMap = new HashMap<String,String>();
            
            // goes through the entire error that @valid gives and picks what we want
            for(FieldError error : result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        // if we dont add @valid we get an 500 internal error when not adding a summary to the project
        // with @valid we get a more explained error of whats going on
        ProjectTask newPT = projectTaskService.saveOrUpdateProjectTask(projectTask); // CREATING NEW PROJECT
        return new ResponseEntity<ProjectTask>(newPT, HttpStatus.CREATED);
    }

    // Reading from DB

    // gets all data from db
    @GetMapping("/all")
    public Iterable<ProjectTask> getAllPTs(){
        return projectTaskService.findAll();
    }

    // gets specificy data from db with ID
    @GetMapping("/{pt_id}")
    public ResponseEntity<?> getPTById(@PathVariable Long pt_id){
        ProjectTask projectTask = projectTaskService.findById(pt_id);

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @DeleteMapping("/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable Long pt_id){
        projectTaskService.delete(pt_id);
        
        return new ResponseEntity<String>("Project Task Deleted",HttpStatus.OK);
    }

    
}
