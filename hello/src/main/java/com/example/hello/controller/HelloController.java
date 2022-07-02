package com.example.hello.controller;

import com.example.hello.dao.UserDao;
import com.example.hello.domain.User;
import com.example.hello.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author : 刘志超
 * @time : 2021/6/15 14:56
 */
@Controller
@Api
@EnableSwagger2
public class HelloController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    //首页
    @RequestMapping("/")
    public String helloindedx(){
        return "login";
    }

    //跳转到登录页面
    @RequestMapping("/login")
    public String hello(){
        return "login";
    }
   /* //登录
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
    }*/

    //退出系统
    @RequestMapping("/exit")
    @ApiOperation(value = "退出")
    public String logout(@ApiParam(name="session",value="会话",required=true)HttpSession session){
        session.invalidate();
        return "login";
    }
}
