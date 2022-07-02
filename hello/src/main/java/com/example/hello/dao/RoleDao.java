package com.example.hello.dao;

import com.example.hello.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : 刘志超
 * @time : 2021/6/16 10:16
 */
@Mapper
public interface RoleDao {
    List<Role> findAllRole();
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(Integer id);
    Role findRoleById(Integer id);
    List<Role> getPartRoles(String message);
}
