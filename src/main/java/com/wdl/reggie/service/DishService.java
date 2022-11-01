package com.wdl.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdl.reggie.dto.DishDto;
import com.wdl.reggie.entity.Dish;

/**
 * @Author:wudl
 * @creat 2022/10/16 17:24
 * @name reggie
 */


public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void updateWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void deleteWithFlavor(Long dishId);
}
