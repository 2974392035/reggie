package com.wdl.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdl.reggie.dto.SetmealDto;
import com.wdl.reggie.entity.Setmeal;

/**
 * @Author:wudl
 * @creat 2022/10/16 17:24
 * @name reggie
 */
public interface SetmealService extends IService<Setmeal> {

    void saveWithSetmealDish(SetmealDto setmealDto);

    void removeWithSetmealDish(Long id);

    SetmealDto getByIdWithSetmealDish(Long id);
}
