package com.if42.tester.service;

import com.if42.tester.entity.Group;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;


public interface UserService extends UserDetailsService{

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public List<User> findAllUsers();

    public List<User> findTeachersByGroup();

    public void addUser(User user);

    public User findUserByUsername(String Username);

    public boolean isExist(User user);

    public Page<User> findUsersByGroup(Integer groupId, Pageable pageable);

    public void removeUser(Integer userId);

    public User findUserById(Integer id);

    public List<User> findUserByGroup(Group group);

    public Collection<? extends GrantedAuthority> getAuthorities(Integer role);

    public List<String> getRoles(Integer role);

    public List<GrantedAuthority> getGrantedAuthorities(List<String> roles);

    public Page<User> findAllByPage(PageRequest pageRequest);

    public Page<User> findAllTeachersAndStudentsByPage(Pageable pageable);

    public Page<User> findAllStudentsByPage(Pageable pageable);




}
