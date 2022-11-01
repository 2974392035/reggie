package com.wdl.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdl.reggie.entity.Employee;
import com.wdl.reggie.mapper.EmployeeMapper;
import com.wdl.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author:wudl
 * @creat 2022/10/8 14:56
 * @name reggie
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
