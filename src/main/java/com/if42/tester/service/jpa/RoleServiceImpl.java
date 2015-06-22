package com.if42.tester.service.jpa;

import com.if42.tester.entity.Role;
import com.if42.tester.repository.RoleRepository;
import com.if42.tester.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service("roleService")
@Repository
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role findRoleById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Role> findTeacherAndStudentRoles(){
        return roleRepository.findTeacherAndStudentRoles();
    }

    @Transactional(readOnly = true)
    public List<Role> findStudentRoles(){
        return roleRepository.findStudentRoles();
    }

}