package ru.nc.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.nc.portal.model.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nc.portal.model.dto.SubjectDTO;
import ru.nc.portal.service.SubjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "/subjects")
    public List<Subject> getSubjects(){
        return subjectService.getAll();
    }

    @PostMapping(value = "/subjects")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody SubjectDTO subject){
        return new ResponseEntity<>(subjectService.createSubject(subject), HttpStatus.CREATED);
    }

    @GetMapping(value = "/subjects/{id}")
    public Subject getSubjectById(@PathVariable("id") Long id){
        return subjectService.getById(id);
    }


}
