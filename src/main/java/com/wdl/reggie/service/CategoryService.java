package com.wdl.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdl.reggie.entity.Category;

/**
 * @Author:wudl
 * @creat 2022/10/14 11:40
 * @name reggie
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据categoryId查询dish_id是否有关联，如果有关联则不删除
     */
    void remove(Long categoryId);
}
