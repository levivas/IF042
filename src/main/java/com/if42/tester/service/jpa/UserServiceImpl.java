package com.if42.tester.service.jpa;

import com.if42.tester.entity.Group;
import com.if42.tester.entity.User;
import com.if42.tester.repository.UserRepository;
import com.if42.tester.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUserName(username);

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword().toLowerCase(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(user.getGroup().getRole().getRoleId()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly=true)
    public boolean isExist(User user){
        return userRepository.findByUserName(user.getUserName()) != null;
    }

    public void removeUser(Integer userId) {
        userRepository.delete(userId);
    }

    @Transactional(readOnly=true)
    public User findUserByUsername(String Username)
    {
        return userRepository.findByUserName(Username);
    }

    @Transactional(readOnly = true)
    public User findUserById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findUserByGroup(Group group) {
        return userRepository.findByGroup(group);
    }

    @Override
    public Page<User> findUsersByGroup(Integer groupId,Pageable pageable){
        return userRepository.findByGroup(groupId,pageable);
    }

    @Override
    public List<User> findTeachersByGroup(){return userRepository.findTeachersByGroup();}

    /**
     * Retrieves a collection of {@link org.springframework.security.core.GrantedAuthority} based on a numerical role
     * @param role the numerical role
     * @return a collection of {@link org.springframework.security.core.GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
    /**
     * Converts a numerical role to an equivalent list of roles
     * @param role the numerical role
     * @return list of roles as as a list of {@link String}
     */
    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();
        if (role == 1) {
            roles.add("ROLE_SUPER_ADMIN");
            roles.add("ROLE_TEACHER");
            roles.add("ROLE_ADMIN");
        } else if (role == 2) {
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_TEACHER");
        } else if (role == 3) {
            roles.add("ROLE_TEACHER");
        }else if (role == 4) {
            roles.add("ROLE_STUDENT");
        }
        return roles;
    }

    /**
     * Wraps {@link String} roles to {@link org.springframework.security.core.authority.SimpleGrantedAuthority} objects
     * @param roles {@link String} of roles
     * @return list of granted authorities
     */
    public List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllByPage(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllTeachersAndStudentsByPage(Pageable pageable){
        return userRepository.findAllTeachersAndStudents(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllStudentsByPage(Pageable pageable){
        return userRepository.findAllStudents(pageable);
    }
}
