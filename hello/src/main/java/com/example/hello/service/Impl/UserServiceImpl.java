package com.example.hello.service.Impl;

import com.example.hello.dao.UserDao;
import com.example.hello.domain.User;
import com.example.hello.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 刘志超
 * @time : 2021/6/15 15:02
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    //分页查询
    @Override
    public List<User> getAllUsers(int page, int size) {
        PageHelper.startPage(page,size);
        return userDao.findAllUser();
    }

    @Override
    public List<User> getPartUsers(String message) {
        PageHelper.startPage(1,5);
        return userDao.getPartUsers(message);
    }

    @Override
    public void addUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public void deleteUser(String id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findUserById(String id) {
        return userDao.findUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public List<Integer> findUserRole(String userId) {
        return userDao.findUserRole(userId);
    }

    @Override
    public void addUserRole(String userId, Integer roleId) {
        userDao.addUserRole(userId,roleId);
    }

    @Override
    public User checkName(User user) {
        return userDao.checkName(user);
    }



}
