package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.service.RoleService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;
    @Test
    public void getAllTest() {
        Assertions.assertThat(roleService.getAll().size()).isEqualTo(2);
    }
}
