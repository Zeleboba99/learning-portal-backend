package ru.nc.portal.service;

import ru.nc.portal.model.TestResult;

import java.util.List;

public interface TestResultService {
    void addNewTestResult(TestResult testResult, Long user_id, Long course_id);
    List<TestResult> getAllTestResults(Long user_id, Long course_id);
}
