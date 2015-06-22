package com.if42.tester.repository;

import com.if42.tester.entity.SessionTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTestRepository extends JpaRepository<SessionTest, Integer> {
    public SessionTest findOne(Integer id);
//    public List<SessionTest> findByTestResult(TestsResult testsResult);
//    public List<SessionTest> findByAvailableTest(AvailableTest availableTest);


}