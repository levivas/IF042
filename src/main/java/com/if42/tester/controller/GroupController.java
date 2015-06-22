package com.if42.tester.controller;

import com.if42.tester.entity.Group;
import com.if42.tester.entity.Role;
import com.if42.tester.service.GroupService;
import com.if42.tester.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/group")
public class GroupController {

    final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    private static final int PAGE_SIZE = 5;

    /**
     * Method gets  List with groups that exist in the system and
     * adds it to table.But it show all groups only for "SuperAdmin" and
     * "Admin", "Teacher" will see only own groups
     * @param map - Map that will be mapped to the tableGroup tile
     * @return
     */
    @RequestMapping(value = "group", method = RequestMethod.GET)
    public String listGroup(Map<String, Object> map,
                            @RequestParam(value = "page", defaultValue = "0") Integer page) {

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Group> groupPages = groupService.findAllByPage(pageRequest);

        map.put("currentPage", page);
        map.put("pageCount", groupPages.getTotalPages());
        map.put("groupList", getGroupsByRole(pageRequest));

        logger.info("Getting groups for table");


        return "tableGroup";
    }

    /** Method that return empty form for creating group
     * @param map - model that will be mapped to the creatingGroup tile
     * @return name of the tile where controller will redirect user after this method
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String formAddGroup(Map<String, Object> map){

        map.put("group", new Group());
        map.put("roleList",getRolesByRole());
        map.put("action", "/group/addGroup");

        return "creatingGroup";

    }

    /**
     * Method create user that will come from ui to the database
     * @param group - object of Group class which comes from ui
     * @param result - contains information of correctness of the group which will come from ui
     *                      due to entity restrictions
     * @return - redirects to the main page if the creating was successful and back
     * to the creatingGroup page if bindingResult will have errors.
     */
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public String addGroup(@ModelAttribute("group") @Valid Group group,
                           BindingResult result,
                           Map<String, Object> map,
                           @RequestParam Integer roleSelect) {

        group.setRole(new Role(roleSelect));

        //validation
        if (groupService.isExist(group)) {
            result.rejectValue("name", "error.group", "This name for group is already exist");
        }

        if (result.hasErrors()) {
            map.put("group", group);
            map.put("currentRole", group.getRole());
            map.put("roleList",getRolesByRole());
            map.put("action", "/group/addGroup");
            return "creatingGroup";
        }
        //end validation
        groupService.addGroup(group);

        logger.info("create new Group with Name: "+ group.getName());

        return "redirect:/group";
    }

    /**
     * Method delete group from database
     * @param groupId - id of Group which you want to delete
     * @return - redirects to the main page after deleting group
     */
    @RequestMapping("/deleteGroup")
    public String deleteGroup(@RequestParam(value = "groupId") Integer groupId) {
        groupService.removeGroup(groupId);

        logger.info("delete Group with id" + groupId);

        return "redirect:/group";
    }

    /**
     * Method gets group by id that was set in url and maps him to the creatingGroup-tile model
     * @param groupId  - identificator of the group whose profile you want to edit
     * @param map - map that will be mapped to the creatingUser tile
     * @return name of the tile where controller will redirect user after this method
     * */
    @RequestMapping(value = "/editGroup", method = RequestMethod.GET)
    public String showEditGroup(Map<String, Object> map,
                                @RequestParam(value = "groupId") Integer groupId) {


        Group group = groupService.findGroupById(groupId);
        map.put("currentRole", group.getRole());
        map.put("group", group);
        map.put("roleList",getRolesByRole());
        map.put("action", "/group/addEdit?groupId=" + group.getGroupId());

        return "creatingGroup";

    }

    /**
     * Method updates group profile that will come from ui to the database
     * @param group - object of Group class which comes from ui
     * @param result - contains information of correctness of the group which will come from ui
     *                      due to entity restrictions
     * @return - redirects to the main page if the creating was successful and back
     * to the creatingGroup page if bindingResult will have errors.
     */
    @RequestMapping(value = "/addEdit", method = RequestMethod.POST)
    public String editGroup(@ModelAttribute("group") @Valid Group group, BindingResult result,
                            Map<String, Object> map,
                            @RequestParam(value = "roleSelect") Integer roleSelect,
                            @RequestParam(value = "groupId") Integer groupId) {

        group.setRole(new Role(roleSelect));

        String groupName = groupService.findGroupById(groupId).getName(); // get group's name from DB
        if (groupService.isExist(group) && !(groupName.equals(group.getName()))) {
            result.rejectValue("name", "error.group", "This name for group is already exist");
        }

        //validation
        if (result.hasErrors()) {
            map.put("group", group);
            map.put("currentRole", group.getRole());
            map.put("roleList",getRolesByRole());
            map.put("action", "/group/addEdit?groupId=" + group.getGroupId());
            return "creatingGroup";
        }

        group.setGroupId(groupId);
        groupService.addGroup(group);

        logger.info("edit Group with Name: " + group.getName());

        return "redirect:/group";
    }//end validation

    public List<Role> getRolesByRole( ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) auth.getAuthorities();
        if (list.contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {
            //all roles
            return roleService.findAllRoles();
        } else if (list.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            //roles of teacher and student
            return roleService.findTeacherAndStudentRoles();
        } else {
            // role of student
            return roleService.findStudentRoles();
        }
    }


    public List<Group> getGroupsByRole(PageRequest pageRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) auth.getAuthorities();
        if (list.contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {
            //all groups
            return groupService.findAllByPage(pageRequest).getContent();
        } else if (list.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            //groups of teacher and students
            return groupService.findAllTeachersAndStudentsGroupsPage(pageRequest).getContent();
        } else {
            //groups of students
            return groupService.findAllStudentsGroupsPage(pageRequest).getContent();
        }
    }
}