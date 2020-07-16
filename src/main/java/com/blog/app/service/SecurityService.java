package com.blog.app.service;

import com.blog.app.entity.Group;
import com.blog.app.entity.User;
import com.blog.app.repository.GroupRepository;
import com.blog.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.List;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserBuilder userBuilder;
        if (user != null) {
            userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.disabled(!user.isActive());
            userBuilder.password(user.getPassword());

            String[] authoritiesArr = getAuthorities(user.getGroupList(), user.getGroupList().size());
            userBuilder.authorities(authoritiesArr);
        } else {
            throw new UsernameNotFoundException("User not found !!!");
        }
        return userBuilder.build();
    }


    private String[] getAuthorities(List<Group> groupList, int size) {
        String[] groupListArr = new String[size];
        for (Group group : groupList) {
            size--;
            groupListArr[size] = group.getGroup_name();
        }
        return groupListArr;
    }
}


// rolelarin paylanmasi qanunun XD //
// user - usertable
// role - userin ede bileceyi actionlar
// group - admin, user, moderator
// group_Role - dictionary tabledir role ve qrupu birleshdirir.
// group_User - dictionary group ve useri bir biri ile elaqelendir.
// sonda springe groupu authorities kimi load edeceyik