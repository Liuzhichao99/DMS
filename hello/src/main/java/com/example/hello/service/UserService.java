package com.example.hello.service;

import com.example.hello.domain.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author : 刘志超
 * @time : 2021/6/15 15:01
 */
public interface UserService {
    List<User> getPartUsers(String message);

    void  addUser(User user);

    void deleteUser(String id);

    User findUserById(String id);

    void updateUser(User user);

    List<Integer> findUserRole(String userId);

    void addUserRole(String userId, Integer roleId);

    User checkName(User user);

    List<User> getAllUsers(int page, int size);
}


