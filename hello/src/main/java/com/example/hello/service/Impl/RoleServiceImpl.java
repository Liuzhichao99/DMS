package com.example.hello.service.Impl;

import com.example.hello.dao.RoleDao;
import com.example.hello.domain.Role;
import com.example.hello.service.RoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;
    @Override
    public List<Role> getAllRoles(int page, int size) {
        PageHelper.startPage(page,size);
        return roleDao.findAllRole();
    }

    @Override
    public List<Role> getPartRoles(String message) {
        PageHelper.startPage(1,5);
        return roleDao.getPartRoles(message);
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public void deleteRole(Integer id) {
        roleDao.deleteRole(id);
    }
}
