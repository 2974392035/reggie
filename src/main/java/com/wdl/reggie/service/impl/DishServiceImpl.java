package com.wdl.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdl.reggie.dto.DishDto;
import com.wdl.reggie.entity.Dish;
import com.wdl.reggie.entity.DishFlavor;
import com.wdl.reggie.mapper.DishMapper;
import com.wdl.reggie.service.DishFlavorService;
import com.wdl.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:wudl
 * @creat 2022/10/16 17:25
 * @name reggie
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService dishFlavorService;

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long id = dishDto.getId();
        flavors = flavors.stream().map(fla -> {
                    fla.setDishId(id);
                    return fla;
                }
        ).collect(Collectors.toList());

        dishFlavorService.updateBatchById(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());

        List<DishFlavor> list = dishFlavorService.list(wrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }


    @Transactional
    @Override
    public void deleteWithFlavor(Long dishId) {
        //删除dishFlavor中的数据
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dishId);
        dishFlavorService.remove(wrapper);

        this.removeById(dishId);
    }
}
