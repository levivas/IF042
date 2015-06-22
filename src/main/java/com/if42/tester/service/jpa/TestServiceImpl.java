package com.if42.tester.service.jpa;

import com.if42.tester.entity.Test;
import com.if42.tester.repository.TestRepository;
import com.if42.tester.service.CategoryService;
import com.if42.tester.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service("testService")
@Repository
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<Test> findAllTest() {
        return testRepository.findAll();
    }

    @Transactional(readOnly = true)
    public String getTestNameByTestId(Integer testId){
        return testRepository.findOne(testId).getName();
    }

    @Transactional(readOnly = true)
    public String getCategoryNameByCategoryId(Integer categoryId){
        return categoryService.findCategoryById(categoryId).getTitle();
    }

    @Transactional(readOnly = true)
    public List<Test> findByTestIdXml(Integer id) {
        List<Test> test = new ArrayList<Test>();
        test.add(testRepository.findByTestId(id));
        return test;
    }

    @Transactional(readOnly = true)
    public List<Test> findByCategoryIdXml(Integer id) {
        return testRepository.findByCategoryId(id);
    }

    @Transactional
    public Test addTest(Test test) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        test.setCreatedBy(name);
        Date date = new Date();
        test.setCreated(new Timestamp(date.getTime()));

        return testRepository.save(test);
    }

    @Transactional
    public void addAll(List<Test> test){
        testRepository.save(test);
    }

    @Transactional
    public Boolean deleteTest(Integer testId) {
        testRepository.delete(testId);
        Test deleted = testRepository.findByTestId(testId);
        return deleted == null;
    }

    @Modifying
    @Transactional
    public Test updateTest(Test test) {
        setModified(test);

        return testRepository.save(test);
    }

    @Transactional(readOnly = true)
    public Test findTestById(Integer id) {
        return testRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public Test findTestByName(String name) { return testRepository.findByName(name); }

    @Transactional(readOnly = true)
    public Page<Test> findAllByPage(Pageable page) {
        return testRepository.findAll(page);
    }

    public Page<Test> getTestPage(Integer pageNumber) {
        PageRequest request =
                new PageRequest(pageNumber - 1, 10, Sort.Direction.DESC, "startTime");
        return testRepository.findAll(request);
    }

    @Transactional(readOnly = true)
    public List<Test> findTestByCategoryId(int id) {
        return testRepository.findByCategory(categoryService.findCategoryById(id));

    }

    @Transactional(readOnly = true)
    public Page<Test> findPageByCategoryId(int id, Pageable pageable) {
        return testRepository.findPageByCategoryId(id, pageable);

    }

    public Page<Test> findFromCategoryByPage(Integer categoryId, PageRequest pageRequest) {
        return testRepository.findPageByCategoryId(categoryId, pageRequest);
    }

    @Transactional
    public void setModified(Test test) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        test.setEditedBy(name);
        Date date = new Date();
        test.setEdited(new Timestamp(date.getTime()));
        testRepository.save(test);
    }

    @Transactional(readOnly=true)
    public boolean testExists(Test test){
        return testRepository.findByName(test.getName())!= null;
    }
}
