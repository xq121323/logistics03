package com.xq.web.controller;

import com.xq.bean.Employee;
import com.xq.bean.EmployeePojo;
import com.xq.bean.Station;
import com.xq.service.EmployeeService;
import com.xq.service.StationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Controller
public class UserController {

    @RequestMapping("/hello")
    public ModelAndView index(ModelAndView mv){
        System.out.println("员工");
        mv.setViewName("redirect:/index.html");
        return mv;
    }


    @Resource
    private EmployeeService employeeService;
    @Resource
    private StationService stationService;



    //查询所有管理人员
    @RequestMapping("/admin-list.html")
    public ModelAndView getAll(ModelAndView mv){
        List<Employee> employee = employeeService.getAll();
//        System.out.println("login---------"+employee);
        //将获取到的管理员信息添加域中
        mv.addObject("employee",employee);
        mv.setViewName("admin-list");
        return mv;
    }
    @RequestMapping("/admin-add.html")
    public Model getName(Model model){
        List<Station> stations = stationService.getStationname();
        model.addAttribute("stations",stations);
        return model;
    }

    //管理员注册
    @RequestMapping("/UserRegister")
    @ResponseBody
    public EmployeePojo employeeRegist(EmployeePojo employee) throws ParseException {
        System.out.println("employee--------"+employee);
//     employeeService.EmployeeRegist(employee);

        return employee;
    }

    @RequestMapping("/unAuth")
    public String unAuth(){
        return "unAuth";
    }

    /**
     *    使用shiro 验证登录
     * @param name
     * @param password
     * @param mv
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(String name,String password, ModelAndView mv){
      //使用shiro编写认证操作
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户信息
        UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        System.out.println("user-----------"+name+password);
        try {
            subject.login(token);
            mv.setViewName("redirect:/index.html");
            System.out.println(222);
            return mv;
        } catch (Exception e) {
            //登录失败：用户名不存在或密码错误
            mv.setViewName("login.html");
            mv.addObject("msg","用户名或密码错误");
            System.out.println(111);
            return mv;
        }
    }
}
