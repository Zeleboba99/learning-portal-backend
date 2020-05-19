package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.User;
import ru.nc.portal.model.dto.SubjectDTO;
import ru.nc.portal.repository.SubjectRepository;
import ru.nc.portal.service.SubjectService;
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class SubjectServiceImplTest {

    @Autowired
    private SubjectService subjectService;

    @Test
    public void getAllTest() {
        Assertions.assertThat(subjectService.getAll().size()).isEqualTo(4);
    }

    @Test
    public void createSubjectTest() {
        SubjectDTO sd = new SubjectDTO("new subject");
        subjectService.createSubject(sd);
        Assertions.assertThat(subjectService.getAll().size()).isEqualTo(5);
    }
}
