package com.wdl.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdl.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:wudl
 * @creat 2022/10/16 17:22
 * @name reggie
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
