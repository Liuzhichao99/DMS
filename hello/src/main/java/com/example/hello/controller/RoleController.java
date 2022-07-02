package com.example.hello.controller;

import com.example.hello.dao.RoleDao;
import com.example.hello.domain.Role;
import com.example.hello.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//角色控制器
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    RoleDao roleDao;

    //获取全部角色信息
    @RequestMapping("/getAllRoles")
    @ResponseBody
    public ModelAndView getAllRoles(String msg, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int size) {
        ModelAndView modelAndView = new ModelAndView();
        List<Role> pageRoles = roleService.getAllRoles(page,size);
        List<Role> allRoles= roleDao.findAllRole();
        PageInfo pageInfo=new PageInfo(pageRoles);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("totalPage",(int)Math.ceil(allRoles.size()*1.0/5));
        modelAndView.addObject("total",allRoles.size());
        //提示角色成功或者失败
        if (msg!=null) {
            modelAndView.addObject("msg",msg);
        }
        modelAndView.setViewName("role-list");
        return modelAndView;
    }

    //根据角色输入查找角色信息
    @RequestMapping("/findRoleBySearch")
    @ResponseBody
    public ModelAndView findRoleBySearch(String message) {
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = roleService.getPartRoles(message);
        List<Role> allRoles=roleDao.getPartRoles(message);
        PageInfo pageInfo=new PageInfo(roles);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("totalPage",(int)Math.ceil(allRoles.size()*1.0/5));
        modelAndView.addObject("total",allRoles.size());
        modelAndView.setViewName("role-list");
        return modelAndView;
    }
    //添加角色信息
    @RequestMapping("/addRoleMessage")
    @ResponseBody
    public ModelAndView addRoleMessage(Role role, Model model) {
        String msg="";
        try {
            roleService.addRole(role);
            msg="添加成功";
        }catch (Exception e){
            msg="添加失败，角色已经存在";
            e.printStackTrace();
        }
        model.addAttribute("msg",msg);
        return new ModelAndView("redirect:/role/getAllRoles");
    }


    //跳转到角色添加页面
    @RequestMapping("/toAddRole")
    @ResponseBody
    public ModelAndView toAddRole() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("role-add");
        return modelAndView;
    }


    //修改角色信息
    @RequestMapping("/updateRoleMessage")
    @ResponseBody
    public ModelAndView updateRoleMessage(Role role, Model model) {
        String msg="";
        try{
            roleService.updateRole(role);
            msg="修改成功";
        }catch (Exception e){
            msg="修改失败";
        }
        model.addAttribute("msg",msg);
        return new ModelAndView("redirect:/role/getAllRoles");
    }

    //根据id查询角色信息跳转到修改页面
    @RequestMapping("/toUpdateRole")
    @ResponseBody
    public ModelAndView toUpdateRole(Integer id) {
        //查询角色信息
        ModelAndView modelAndView = new ModelAndView();
        Role role = roleService.findRoleById(id);
        modelAndView.addObject("role", role);
        modelAndView.setViewName("role-update");
        return modelAndView;
    }

    //删除角色信息
    @RequestMapping("/deleteRoleMessage")
    @ResponseBody
    public ModelAndView deleteRoleMessage(Integer id) {
        roleService.deleteRole(id);
        return new ModelAndView("redirect:/role/getAllRoles");
    }

}
