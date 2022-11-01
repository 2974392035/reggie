package com.wdl.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdl.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:wudl
 * @creat 2022/10/14 11:42
 * @name reggie
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
