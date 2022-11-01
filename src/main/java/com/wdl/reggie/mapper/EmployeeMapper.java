package com.wdl.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdl.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:wudl
 * @creat 2022/10/8 14:53
 * @name reggie
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
