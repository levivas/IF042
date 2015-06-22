package com.if42.tester.repository;


import com.if42.tester.entity.Group;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>,PagingAndSortingRepository<User, Integer> {
    public User findByUserName(String username);

    public List<User> findByGroup(Group group);

    @Query(value = "SELECT u  FROM User u WHERE u.group.groupId = :groupId  ORDER BY u.surname ASC" )
    public Page<User> findByGroup(@Param("groupId") Integer groupId, Pageable pageable);

    public final static int TEACHER_ID = 3;
    public final static int STUDENT_ID = 4;

    @Query("SELECT u FROM User u WHERE u.group.groupId = "+ TEACHER_ID)
    public List<User> findTeachersByGroup();

    @Query("SELECT u FROM User u WHERE u.group.role.roleId = "+ TEACHER_ID + " OR u.group.role.roleId = " + STUDENT_ID
            +" order by u.surname desc " )
    public Page<User> findAllTeachersAndStudents(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.group.role.roleId = "+ STUDENT_ID
            +" order by u.surname desc ")
    public Page<User> findAllStudents(Pageable pageable);

}
