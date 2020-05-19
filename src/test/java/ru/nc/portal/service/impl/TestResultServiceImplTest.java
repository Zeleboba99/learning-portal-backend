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
import ru.nc.portal.model.TestResult;
import ru.nc.portal.service.TestResultService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class TestResultServiceImplTest {
    @Autowired
    private TestResultService testResultService;
    @Test
    public void addNewTestResultTest() {
        TestResult testResult = new TestResult();
        Assertions.assertThat(testResultService.getAllTestResults(1L,1L).size()).isEqualTo(0);
        testResultService.addNewTestResult(testResult,1L,1L);
        Assertions.assertThat(testResultService.getAllTestResults(1L,1L).size()).isEqualTo(1);
    }

    @Test
    public void getAllTestResultsTest() {
        TestResult testResult1 = new TestResult();
        TestResult testResult2 = new TestResult();
        TestResult testResult3 = new TestResult();
        Assertions.assertThat(testResultService.getAllTestResults(1L,1L).size()).isEqualTo(0);
        testResultService.addNewTestResult(testResult1,1L,1L);
        testResultService.addNewTestResult(testResult2,1L,1L);
        testResultService.addNewTestResult(testResult3,1L,1L);
        Assertions.assertThat(testResultService.getAllTestResults(1L,1L).size()).isEqualTo(3);
    }
}
