package com.if42.tester.service;

import com.if42.tester.entity.GraphPoint;
import com.if42.tester.entity.Test;
import com.if42.tester.entity.TestsResult;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface TestsResultService {

    public List<TestsResult> findAllTestsResults();

    public void addTestsResult(TestsResult testsResult);

    public void removeTestsResult(TestsResult testsResult);

    public void removeTestsResult(Integer id);

    public TestsResult findTestsResultById(Integer id);

    public List<TestsResult> findTestsResultByUser(User user);

    public List<TestsResult> findTestsResultByTest(Test test);

    public Page<Object []> findByGroupForRating(Integer groupId, Integer categoryId, Pageable pageable);

    public Page<Object []> findCategoriesAverageForRating(Integer groupId, Pageable pageable);

    public Page<TestsResult> findByTestGroupCategory(Integer testId, Integer groupId, Integer categoryId,
                                                     Timestamp dateA, Timestamp dateB, Pageable pageable);

    public Page<TestsResult> findAllByPage(Pageable pageable);

    public Page<TestsResult> findByTestCategoryUserId(Integer testId, Integer categoryId,
                                                      Timestamp dateA, Timestamp dateB, Integer userId, Pageable pageable);

    public List<GraphPoint> getAverageForCategories(Integer userId);

    public List<GraphPoint> getMaxForCategories(Integer userId);

    public List<GraphPoint> getMinForCategories(Integer userId);

    public List<TestsResult>findByTestGroupCategoryList(Integer testId, Integer groupId, Integer categoryId,
                                                        Timestamp dateA, Timestamp dateB);

    public List<GraphPoint> getTestStats(Integer testId);

    public List<GraphPoint> getStatsForUserTest(Integer testId, Integer userId);

}