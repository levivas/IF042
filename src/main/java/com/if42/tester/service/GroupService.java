package com.if42.tester.service;

import com.if42.tester.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface GroupService {
	
	public List<Group> findAllGroup();

    public List<Group> findAllStudentGroup();

	public void addGroup(Group group);

	public void removeGroup(Integer groupId);

	public Group findGroupById(Integer id);

    public Page<Group> findAllByPage(Pageable pageable);

    public Page<Group> findAllTeachersAndStudentsGroupsPage(Pageable pageable);

    public Page<Group> findAllStudentsGroupsPage(Pageable pageable);

    public Group findByName(String name);

    public boolean isExist(Group group);

    public List<Group> findAllTeachersAndStudentsGroups();

    public List<Group> findAllStudentsGroups();

    public List<Group> getGroupsByRole();

}
