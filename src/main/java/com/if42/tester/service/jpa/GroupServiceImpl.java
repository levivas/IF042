package com.if42.tester.service.jpa;

import com.if42.tester.entity.Group;
import com.if42.tester.repository.GroupRepository;
import com.if42.tester.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("groupService")
@Repository
@Transactional
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	GroupRepository groupRepository;

    @Transactional(readOnly=true)
	public List<Group> findAllGroup(){
		return groupRepository.findAll();
	}

    @Transactional(readOnly=true)
	public List<Group> findAllStudentGroup(){
		return groupRepository.findAllStudentGroup();
	}

	public void addGroup(Group group) {
		groupRepository.save(group);
	}

	public void removeGroup(Integer groupId) {
		groupRepository.delete(groupId);
	}

    @Transactional(readOnly=true)
	public Group findGroupById(Integer id) {
		return groupRepository.findOne(id);
	}

    @Transactional(readOnly=true)
    public Page<Group> findAllByPage(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Transactional(readOnly=true)
    public Page<Group> findAllTeachersAndStudentsGroupsPage(Pageable pageable){
        return groupRepository.findAllTeachersAndStudentsGroupsPage(pageable);
    }

    @Transactional(readOnly=true)
    public Page<Group> findAllStudentsGroupsPage(Pageable pageable){
        return groupRepository.findAllStudentsGroupsPage(pageable);
    }


    @Transactional(readOnly=true)
    public Group findByName(String name){
        return  groupRepository.findByName(name);
    }

    @Transactional(readOnly=true)
    public boolean isExist(Group group){
        return groupRepository.findByName(group.getName())!= null;
    }

    @Transactional(readOnly=true)
    public List<Group> findAllTeachersAndStudentsGroups(){
        return groupRepository.findAllTeachersAndStudentsGroups();
    }

    @Transactional(readOnly=true)
    public List<Group> findAllStudentsGroups(){
        return groupRepository.findAllStudentsGroups();
    }


    /**
     * This method returns list of all groups or teacher's and
     * student's groups or only student's groups. SuperAdmin will see all
     * groups, Admin - teacher's and student's groups, Teacher - student's
     * groups
     *
     * @return List of group
     *
     */
    @Transactional(readOnly=true)
    public List<Group> getGroupsByRole(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) auth.getAuthorities();
        if (list.contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))){
            //all groups
            return findAllGroup();
        } else if (list.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            //groups of teachers and users
            return findAllTeachersAndStudentsGroups();
        } else {
            //groups of students
            return findAllStudentsGroups();
        }
    }

}
