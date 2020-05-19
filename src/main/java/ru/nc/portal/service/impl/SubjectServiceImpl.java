package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.Subject;
import ru.nc.portal.model.dto.SubjectDTO;
import ru.nc.portal.repository.SubjectRepository;
import ru.nc.portal.service.SubjectService;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAll(){
        return subjectRepository.findAll();
    }

    @Override
    public Subject createSubject(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public Subject getById(Long id) {
        return subjectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Subject", "id", id));
    }
}
