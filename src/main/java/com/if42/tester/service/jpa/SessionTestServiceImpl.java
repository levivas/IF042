package com.if42.tester.service.jpa;

import com.if42.tester.entity.SessionTest;
import com.if42.tester.repository.SessionTestRepository;
import com.if42.tester.service.SessionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("sessionTestService")
@Repository
@Transactional
public class SessionTestServiceImpl implements SessionTestService {

    @Autowired
    private SessionTestRepository sessionTestRepository;

    @Transactional(readOnly = true)
    public List<SessionTest> findAllSessionTests() {
        return sessionTestRepository.findAll();
    }

    public void addSessionTest(SessionTest sessionTest) {
        sessionTestRepository.save(sessionTest);
    }

    public void removeSessionTest(SessionTest testsResult) {
        sessionTestRepository.delete(testsResult);
    }

    public void removeSessionTest(Integer id) {
        sessionTestRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public SessionTest findSessionTestById(Integer id) {
        return sessionTestRepository.findOne(id);
    }
}
