package com.if42.tester.repository;

import com.if42.tester.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer>, PagingAndSortingRepository<Group, Integer> {

    public final static int TEACHER_ROLE_ID = 3;
    public final static int STUDENT_ROLE_ID = 4;

    public Group findByName(String name);

    @Query("SELECT g FROM Group g WHERE g.role.roleId ="+ STUDENT_ROLE_ID)
    public List<Group> findAllStudentGroup();

    @Query("SELECT g FROM Group g WHERE g.role.roleId = "+ TEACHER_ROLE_ID + " OR g.role.roleId = " + STUDENT_ROLE_ID)
    public List<Group> findAllTeachersAndStudentsGroups();

    @Query("SELECT g FROM Group g WHERE g.role.roleId = "+ STUDENT_ROLE_ID)
    public List<Group> findAllStudentsGroups();

    @Query("SELECT g FROM Group g WHERE g.role.roleId = "+ TEACHER_ROLE_ID + " OR g.role.roleId = " + STUDENT_ROLE_ID)
    public Page<Group> findAllTeachersAndStudentsGroupsPage(Pageable pageable);

    @Query("SELECT g FROM Group g WHERE g.role.roleId = "+ STUDENT_ROLE_ID)
    public Page<Group> findAllStudentsGroupsPage(Pageable pageable);
}
