package com.wdl.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdl.reggie.common.CustomException;
import com.wdl.reggie.entity.Category;
import com.wdl.reggie.entity.Dish;
import com.wdl.reggie.entity.Setmeal;
import com.wdl.reggie.mapper.CategoryMapper;
import com.wdl.reggie.service.CategoryService;
import com.wdl.reggie.service.DishService;
import com.wdl.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:wudl
 * @creat 2022/10/14 11:41
 * @name reggie
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(Long categoryId) {
        LambdaQueryWrapper<Dish> wrapper01 = new LambdaQueryWrapper<>();
        wrapper01.eq(Dish::getCategoryId,categoryId);

        int dishCount = dishService.count(wrapper01);

        if (dishCount > 0) {
            throw new CustomException("当前种类与菜品有关联，删除失败");
        }

        LambdaQueryWrapper<Setmeal> wrapper02 = new LambdaQueryWrapper<>();
        wrapper02.eq(Setmeal::getCategoryId,categoryId);

        int setmealCount = setmealService.count(wrapper02);

        if (setmealCount > 0) {
            throw new CustomException("当前种类与套餐有关联，删除失败");
        }

        //删除成功
        removeById(categoryId);
    }
}
