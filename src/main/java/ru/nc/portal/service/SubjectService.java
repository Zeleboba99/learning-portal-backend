package ru.nc.portal.service;

import ru.nc.portal.model.Subject;
import ru.nc.portal.model.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {
    List<Subject> getAll();
    Subject createSubject(SubjectDTO subject);
    Subject getById(Long id);
}
