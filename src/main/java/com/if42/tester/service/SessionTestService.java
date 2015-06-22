package com.if42.tester.service;

import com.if42.tester.entity.SessionTest;

import java.util.List;

public interface SessionTestService {

    public List<SessionTest> findAllSessionTests();

    public void addSessionTest(SessionTest sessionTest);

    public void removeSessionTest(SessionTest testsResult);

    public void removeSessionTest(Integer id);

    public SessionTest findSessionTestById(Integer id);
}
