package com.if42.tester.service.jpa;

import com.if42.tester.entity.GraphPoint;
import com.if42.tester.entity.Test;
import com.if42.tester.entity.TestsResult;
import com.if42.tester.entity.User;
import com.if42.tester.repository.TestsResultRepository;
import com.if42.tester.service.TestsResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service("testsResultService")
@Repository
@Transactional
public class TestsResultServiceImpl implements TestsResultService {

    @Autowired
    private TestsResultRepository testsResultRepository;


    @Transactional(readOnly = true)
    public List<TestsResult> findAllTestsResults() {
        return testsResultRepository.findAll();
    }

    public void addTestsResult(TestsResult testsResult) {
        testsResultRepository.save(testsResult);
    }

    public void removeTestsResult(TestsResult testsResult) {
        testsResultRepository.delete(testsResult);
    }

    public void removeTestsResult(Integer id) {
        testsResultRepository.delete(id);
    }


    @Transactional(readOnly = true)
    public TestsResult findTestsResultById(Integer id) {
        return testsResultRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<TestsResult> findTestsResultByUser(User user) { return testsResultRepository.findByUser(user); }

    @Transactional(readOnly = true)
    public List<TestsResult> findTestsResultByTest(Test test) { return testsResultRepository.findByTest(test); }

    @Transactional
    public Page<Object []> findByGroupForRating(Integer groupId,Integer categoryId, Pageable pageable){
        return testsResultRepository.findByGroupForRatingUser(groupId, categoryId, pageable);
    }

    @Transactional
    public Page<Object []> findCategoriesAverageForRating(Integer groupId,Pageable pageable){
        return testsResultRepository.getCategoriesAverageForRating(groupId,pageable);
    }


    @Transactional(readOnly = true)
    public Page<TestsResult> findByTestGroupCategory(Integer testId, Integer groupId, Integer categoryId,
                                                     Timestamp dateA, Timestamp dateB, Pageable pageable) {
        return testsResultRepository.findByTestGroupCategory(testId, groupId, categoryId,
                dateA, dateB, pageable);
    }

    @Transactional(readOnly=true)
    public Page<TestsResult> findAllByPage(Pageable pageable) {
        return testsResultRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<TestsResult> findByTestCategoryUserId(Integer testId, Integer categoryId,
                                                      Timestamp dateA, Timestamp dateB, Integer userId, Pageable pageable) {
        return testsResultRepository.findByTestCategoryUserId(testId, categoryId,
                dateA, dateB, userId, pageable);
    }

    @Transactional(readOnly = true)
    public List<GraphPoint> getAverageForCategories(Integer userId) {
        List<GraphPoint> graphPoints = new ArrayList<GraphPoint>();
        for(Object[] o : testsResultRepository.getCategoriesAverage(userId)) {
            graphPoints.add(new GraphPoint((Double)o[1], (String)o[0]));
        }
        return graphPoints;
    }

    @Transactional(readOnly = true)
    public List<GraphPoint> getMaxForCategories(Integer userId) {
        List<GraphPoint> graphPoints = new ArrayList<GraphPoint>();
        for(Object[] o : testsResultRepository.getCategoriesMax(userId)) {
            graphPoints.add(new GraphPoint((Integer)o[1], (String)o[0]));
        }
        return graphPoints;
    }

    @Transactional(readOnly = true)
    public List<GraphPoint> getMinForCategories(Integer userId) {
        List<GraphPoint> graphPoints = new ArrayList<GraphPoint>();
        for(Object[] o : testsResultRepository.getCategoriesMin(userId)) {
            graphPoints.add(new GraphPoint((Integer)o[1], (String)o[0]));
        }
        return graphPoints;
    }

    @Transactional(readOnly = true)
    public List<TestsResult>findByTestGroupCategoryList(Integer testId, Integer groupId, Integer categoryId,
                                                        Timestamp dateA, Timestamp dateB) {
        return testsResultRepository.findByTestGroupCategoryList(testId, groupId, categoryId, dateA, dateB);
    }

    @Transactional(readOnly = true)
    public List<GraphPoint> getTestStats(Integer testId) {
        List<GraphPoint> points = new ArrayList<GraphPoint>();
        List<Object[]> objects = testsResultRepository.averageForGroupsByTest(testId);
        for (Object[] o : objects) {
            points.add(new GraphPoint((Double)o[0], (String)o[1]));
        }
        return points;
    }

    @Transactional(readOnly = true)
    public List<GraphPoint> getStatsForUserTest(Integer testId, Integer userId) {
        List<Object[]> objects = testsResultRepository.getResultsForUserTest(testId, userId);
        List<GraphPoint> points = new ArrayList<GraphPoint>();
        for(Object[] obj : objects) {
            String s = obj[1].toString();
            points.add(new GraphPoint((Integer)obj[0], s));
        }
        return points;
    }
}