package com.if42.tester.service;

import com.if42.tester.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {
	
	public List<Test> findAllTest();

    public String getTestNameByTestId(Integer testId);

    public String getCategoryNameByCategoryId(Integer categoryId);

    public List<Test> findByTestIdXml(Integer id);

    public List<Test> findByCategoryIdXml(Integer id);

	public Test addTest(Test test);

    public void addAll(List<Test> test);

	public Boolean deleteTest(Integer testId);

    public Test updateTest(Test test);

	public Test findTestById(Integer id);

    public Test findTestByName(String name);

    public Page<Test> findAllByPage(Pageable page);

    public Page<Test> getTestPage(Integer pageNumber);

    public List<Test> findTestByCategoryId(int id);

    public Page<Test> findPageByCategoryId(int id, Pageable pageable);

    public Page<Test> findFromCategoryByPage(Integer categoryId, PageRequest pageRequest);

    public void setModified(Test test);

    public boolean testExists(Test test);
}
