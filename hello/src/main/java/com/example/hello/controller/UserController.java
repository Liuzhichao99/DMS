package com.example.hello.controller;

import com.example.hello.dao.UserDao;
import com.example.hello.domain.Role;
import com.example.hello.domain.User;
import com.example.hello.service.RoleService;
import com.example.hello.service.UserService;
import org.springframework.stereotype.Controller;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

//控制层
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    //登录
    @RequestMapping("/userLogin")
    @ResponseBody
    public ModelAndView userLogin(User user, Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user1=userService.checkName(user);
        try {
            if (user1==null){
                model.addAttribute("msg","用户名不存在");
                modelAndView.setViewName("login");
                return modelAndView;
            }else if (!user1.getPassword().equals(user.getPassword())){
                model.addAttribute("msg","密码错误");
                modelAndView.setViewName("login");
                return modelAndView;
            }else {
                session.setAttribute("USER",user);
                modelAndView.setViewName("main");
                return modelAndView;
            }
        }catch (Exception e){
            model.addAttribute("msg","服务器异常");
            modelAndView.setViewName("login");
            return modelAndView;
        }
    }

    //获取全部用户信息
    @RequestMapping("/getAllUsers")
    @ResponseBody
    public ModelAndView getAllUsers(String msg,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5")int size) {
        ModelAndView modelAndView = new ModelAndView();
        //查询所有用户
        List<User> pageUsers=userService.getAllUsers(page,size);
        for(User u:pageUsers){
            u.setRoles(userService.findUserRole(u.getId()));
        }
        List<User> allUsers=userDao.findAllUser();
        PageInfo pageInfo=new PageInfo(pageUsers);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("totalPage",(int)Math.ceil(allUsers.size()*1.0/5));
        modelAndView.addObject("total",allUsers.size());
        //循环查询所有用户的角色

        //将提示信息传递给前端
        if(msg!=null){
            modelAndView.addObject("msg",msg);
        }

        //设置你要传递到哪个页面
        modelAndView.setViewName("user-list");
        return modelAndView;
    }

    //根据用户输入查找用户信息
    @RequestMapping("/findUserBySearch")
    @ResponseBody
    public ModelAndView findUserBySearch(String message) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.getPartUsers(message);
        //查找用户对应的角色
        for(User user:users){
            user.setRoles(userService.findUserRole(user.getId()));
        }
        List<User> allUsers=userDao.getPartUsers(message);
        PageInfo pageInfo=new PageInfo(users);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("totalPage",(int)Math.ceil(allUsers.size()*1.0/5));
        modelAndView.addObject("total",allUsers.size());

        modelAndView.setViewName("user-list");
        return modelAndView;
    }
    //添加用户信息
    @RequestMapping("/addUserMessage")
    @ResponseBody
    public ModelAndView addUserMessage(User user,Model model) {
        System.out.println("信息："+user.getRoles());
        //将用户的角色添加到用户_角色表
        List<Integer> roles=user.getRoles();
        String msg="";
        if(roles!=null){
            for(Integer roleId:roles){
                userService.addUserRole(user.getId(),roleId);
            }
        }
        //添加用户信息
        try {
            userService.addUser(user);
            msg="添加成功";
        }catch (Exception e){
            msg="添加失败，用户已存在";
        }
        model.addAttribute("msg",msg);
        return new ModelAndView("redirect:/user/getAllUsers");
    }

    //跳转到用户添加页面
    @RequestMapping("/toAddUser")
    @ResponseBody
    public ModelAndView toAddUser(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5")int size) {
        ModelAndView modelAndView = new ModelAndView();
        //查询所有角色
        List<Role> allRoles = roleService.getAllRoles(page, size);
        modelAndView.addObject("roles", allRoles);
        modelAndView.setViewName("user-add");
        return modelAndView;
    }

    //修改用户信息
    @RequestMapping("/updateUserMessage")
    @ResponseBody
    public ModelAndView updateUserMessage(User user, Model model) {
        //获取用户拥有角色id
        List<Integer> roles=user.getRoles();
        //用户id
        String userId=user.getId();
        //给前端的提示信息
        String msg="";
        //如果用户选择了角色信息，将信息用户_角色表
        if(roles!=null) {
            //添加新选择的用户角色
            for (Integer roleId : roles) {
                try {
                    userService.addUserRole(userId, roleId);
                } catch (Exception e) {
                    System.out.println("添加角色重复");
                }
            }
        }
        //修改用户信息
        try {
            userService.updateUser(user);
            msg="修改成功";
        }catch (Exception e){
            msg="修改失败";
        }
        model.addAttribute("msg",msg);

        return new ModelAndView("redirect:/user/getAllUsers");
    }

    //根据id查询用户信息跳转到修改页面
    @RequestMapping("/toUpdateUser")
    @ResponseBody
    public ModelAndView toUpdateUser(String id,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5")int size) {
        ModelAndView modelAndView = new ModelAndView();
        //根据用户id查询用户所有信息
        User user = userService.findUserById(id);
        //查询当前用户的角色
        user.setRoles(userService.findUserRole(user.getId()));
        //将用户信息传递给前端页面
        modelAndView.addObject("user", user);


        //查询全部角色
        List<Role> allRoles = roleService.getAllRoles(page, size);
        //将角色集合allRoles存到modelAndView传递给前端
        modelAndView.addObject("roles", allRoles);
        modelAndView.setViewName("user-update");
        return modelAndView;
    }

    //删除用户信息
    @RequestMapping("/deleteUserMessage")
    @ResponseBody
    public ModelAndView deleteUserMessage(String id) {
        System.out.println("message="+id);
        //根据用户id删除用户
        userService.deleteUser(id);
        return new ModelAndView("redirect:/user/getAllUsers");
    }
}
