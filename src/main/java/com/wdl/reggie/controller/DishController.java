package com.wdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdl.reggie.common.R;
import com.wdl.reggie.dto.DishDto;
import com.wdl.reggie.entity.Category;
import com.wdl.reggie.entity.Dish;
import com.wdl.reggie.entity.Setmeal;
import com.wdl.reggie.service.CategoryService;
import com.wdl.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:wudl
 * @creat 2022/10/18 20:18
 * @name reggie
 */

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public R<String> dish(@RequestBody DishDto dish) {
        log.info("添加的dish : {}", dish.toString());

//        dishService.save(dish);
        dishService.updateWithFlavor(dish);
        return R.success("菜品添加成功");
    }

    @GetMapping("/page")
    public R<Page<DishDto>> page(@RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "10") int pageSize,
                                 String name) {
        log.info("展示页面page");

        Page<Dish> dishPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage, wrapper);

        //将除了records的其他复制给dishDtoPage
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

        List<Dish> dishPageRecords = dishPage.getRecords();
        List<DishDto> records = dishPageRecords.stream().map(dish -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            Long categoryId = dish.getCategoryId();

            if (!ObjectUtils.isEmpty(categoryId)) {
                Category category = categoryService.getById(categoryId);
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(records);

        return R.success(dishDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("删除菜品及口味 ");
        for (Long id : ids) {
            dishService.deleteWithFlavor(id);
        }
        return R.success("删除成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> getDishByIdWithFlavor(@PathVariable Long id) {
        log.info("回显数据");
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<DishDto> update(@RequestBody DishDto dishDto) {
        log.info("修改数据");
        dishService.updateWithFlavor(dishDto);
        return R.success(dishDto);
    }

    /**
     * 停售/启售
     *
     * @param statusCode 1代表启售
     * @param ids
     * @return
     */
    @PostMapping("/status/{statusCode}")
    public R<String> updateStatus(@PathVariable int statusCode,
                                  @RequestParam List<Long> ids) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getId, ids);

        List<Dish> list = dishService.list(wrapper);
        list = list.stream().map(dish -> {
            dish.setStatus(statusCode);
            return dish;
        }).collect(Collectors.toList());

        //
        log.info("更新");
        dishService.updateBatchById(list);
        return R.success("停售/启售 成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> list(@RequestParam Long categoryId) {

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getCategoryId, categoryId)
                .eq(Dish::getStatus, 1)
                .orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(wrapper);
        return R.success(list);
    }


}
