package com.wdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdl.reggie.common.BaseContext;
import com.wdl.reggie.common.R;
import com.wdl.reggie.entity.Employee;
import com.wdl.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @Author:wudl
 * @creat 2022/10/8 14:59
 * @name reggie
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/aaa")
    public String a() {
        return "/backend/index";
    }

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
//        1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

//        3、如果没有查询到则返回登录失败结果
        if (ObjectUtils.isEmpty(emp)) {
            return R.error("登录失败");
        }
//        4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
//        5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

//        6、登录成功，将员工id存入Session并返回登录成功结果
        HttpSession session = request.getSession();
        session.setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {

//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateTime(LocalDateTime.now());
        //设置初始密码123456 并加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //获取当前登录的用户的id
        Long id = (Long) request.getSession().getAttribute("employee");

//        employee.setCreateUser(id);
//        employee.setUpdateUser(id);

        employeeService.save(employee);

        return R.success("添加员工成功");
    }

    @GetMapping("/page")
    //R<page> page因为list.html 143行
    public R<Page> page(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                        String name) {
        //name 为搜索框中的内容
        Page<Employee> employeePage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), Employee::getName, name)
                .orderByDesc(Employee::getUpdateTime);

        employeeService.page(employeePage, wrapper);

        return R.success(employeePage);
//        1、页面发送ajax请求，将分页查询参数(page、pageSize、name)提交到服务端
//        2、服务端Controller接收页面提交的数据并调用Service查询数据
//        3、Service调用Mapper操作数据库，查询分页数据
//        4、Controller将查询到的分页数据响应给页面
//        5、页面接收到分页数据并通过ElementUI的Table组件展示到页面上
    }


    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);

        log.info(" 查询 == employee : {}", employee);
        if (!ObjectUtils.isEmpty(id)) {
            return R.success(employee);
        }
        return R.error("查询不到用户");
    }

    @PutMapping
    public R<Employee> update(@RequestBody Employee employee, HttpServletRequest request) {

        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);

        return R.success(employee);
    }

}
