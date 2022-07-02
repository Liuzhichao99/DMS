package com.example.hello.service;

import com.example.hello.domain.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author : 刘志超
 * @time : 2021/6/16 10:19
 */
public interface RoleService {
    List<Role> getAllRoles(int page, int size);

    List<Role> getPartRoles(String message);

    void addRole(Role role);

    void updateRole(Role role);

    Role findRoleById(Integer id);

    void deleteRole(Integer id);
}
