package com.wdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdl.reggie.common.R;
import com.wdl.reggie.entity.Category;
import com.wdl.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wudl
 * @creat 2022/10/14 11:28
 * @name reggie
 */

@Slf4j
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public R<Category> save(@RequestBody Category category) {
        categoryService.save(category);

        log.info("category: {}", category);

        return R.success(category);
    }

    @GetMapping("/page")
    public R<Page> page(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        categoryService.page(categoryPage, wrapper);

        return R.success(categoryPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") String id){
        categoryService.remove(new Long(id));

        log.info("删除id: {}",id);

        return R.success("删除成功");
    }

    @PutMapping
    public R<Category> update(@RequestBody Category category){
        categoryService.updateById(category);

        log.info("修改: {}",category);

        return R.success(category);
    }

    /**
     * 菜品列表
     * @param type 值为 1 表示菜品
     * @return 菜品列表
     */
    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam int type){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type)
                .orderByAsc(Category::getSort);
        List<Category> categoryList = categoryService.list(wrapper);
        return R.success(categoryList);
    }
}
