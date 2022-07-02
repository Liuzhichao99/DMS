package com.example.hello.dao;

import com.example.hello.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 刘志超
 * @time : 2021/6/15 15:01
 */
@Mapper
public interface UserDao {
    List<User> findAllUser();
    List<User> getPartUsers(String message);
    void insertUser(User user);
    void deleteUser(String id);
    void updateUser(User user);
    User findUserById(String id);
    List<Integer> findUserRole(String userId);
    void addUserRole(@Param("userId")String userId, @Param("roleId")Integer roleId);
    User checkName(User user);
}
