package com.if42.tester.repository;

import com.if42.tester.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public final static int TEACHER_ROLE_ID = 3;
    public final static int STUDENT_ROLE_ID = 4;


    @Query("SELECT r FROM Role r WHERE r.roleId = "+ TEACHER_ROLE_ID + " OR r.roleId = " + STUDENT_ROLE_ID)
    public List<Role> findTeacherAndStudentRoles();

    @Query("SELECT r FROM Role r WHERE r.roleId = "+ STUDENT_ROLE_ID)
    public List<Role> findStudentRoles();

}