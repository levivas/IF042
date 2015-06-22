package com.if42.tester.service.jpa;

import com.if42.tester.entity.AvailableTest;
import com.if42.tester.entity.User;
import com.if42.tester.repository.AvailableTestRepository;
import com.if42.tester.service.AvailableTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("availableTestService")
@Repository
@Transactional
public class AvailableTestServiceImpl implements AvailableTestService{

	@Autowired
	AvailableTestRepository availableTestRepository;

    @Transactional(readOnly=true)
	public List<AvailableTest> findAllAvailableTest() {
		return availableTestRepository.findAll();
	}

	public void addAvailableTest(AvailableTest availableTest) {
        availableTestRepository.save(availableTest);
    }

    public void removeAvailableTest(AvailableTest availableTest) {
		availableTestRepository.delete(availableTest);
	}

	public void removeAvailableTestById(Integer availableTestId) {
		availableTestRepository.delete(availableTestId);
	}

    @Transactional(readOnly=true)
	public AvailableTest findAvailableTestById(Integer id) {
		return availableTestRepository.findOne(id);
	}

    @Transactional(readOnly=true)
    public Page<AvailableTest> findAllByPage(Pageable pageable) {
        return availableTestRepository.findAll(pageable);
    }

    @Transactional(readOnly=true)
    public Page<AvailableTest> findByUserByPage(Pageable pageable, User user) {
        return availableTestRepository.findByUser(pageable, user);
    }
}
