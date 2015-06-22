package com.if42.tester.service;

import com.if42.tester.entity.Role;
import java.util.List;

public interface RoleService {

    public List<Role> findAllRoles();

    public Role findRoleById(Integer id);

    public List<Role> findTeacherAndStudentRoles();

    public List<Role> findStudentRoles();

}