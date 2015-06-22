package com.if42.tester.service;

import com.if42.tester.entity.AvailableTest;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AvailableTestService {

    public List<AvailableTest> findAllAvailableTest();

    public void addAvailableTest(AvailableTest availableTest);

    public void removeAvailableTest(AvailableTest availableTest);

	public void removeAvailableTestById(Integer availableTestId);

	public AvailableTest findAvailableTestById(Integer id);

    public Page<AvailableTest> findAllByPage(Pageable pageable);

    public Page<AvailableTest> findByUserByPage(Pageable pageable, User user);
}
