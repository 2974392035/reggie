package com.wdl.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdl.reggie.common.R;
import com.wdl.reggie.dto.SetmealDto;
import com.wdl.reggie.entity.Setmeal;
import com.wdl.reggie.service.CategoryService;
import com.wdl.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:wudl
 * @creat 2022/10/21 11:44
 * @name reggie
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> page(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    String name) {
        log.info("套餐分页");
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), Setmeal::getName, name)
                .orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(setmealPage, wrapper);
        List<Setmeal> records = setmealPage.getRecords();

        Page<SetmealDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(setmealPage, dtoPage, "records");

        List<SetmealDto> dtoList = records.stream().map(setmeal -> {
            SetmealDto dto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, dto);
            Long categoryId = setmeal.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            dto.setCategoryName(categoryName);
            return dto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(dtoList);
        return R.success(dtoPage);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("添加套餐");
        setmealService.saveWithSetmealDish(setmealDto);
        return R.success("添加套餐成功");
    }

    /**
     * 停售/启售套餐
     *
     * @param statusCode 1 表示 启售该套餐
     * @param ids        选中的若干id
     * @return
     */
    @PostMapping("/status/{statusCode}")
    public R<String> update(@PathVariable int statusCode,
                            @RequestParam List<Long> ids) {
        log.info("停售/启售套餐 ");
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId, ids);
        List<Setmeal> setmealList = setmealService.list(wrapper);
        setmealList = setmealList.stream().map(setmeal -> {
            setmeal.setStatus(statusCode);
            return setmeal;
        }).collect(Collectors.toList());

        setmealService.updateBatchById(setmealList);
        return R.success("停售/启售套餐成功");
    }


    /**
     * 删除套餐
     * 启售中的不能删除                            <---     ################
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("删除套餐");
        ids.stream().map(id -> {
            setmealService.removeWithSetmealDish(id);
            return id;
        }).collect(Collectors.toList());

        return R.success("删除套餐成功");
    }

    /**
     * 回显套餐信息
     *
     * @param setmealId
     * @return
     */
    @GetMapping("/{setmealId}")
    public R<SetmealDto> get(@PathVariable Long setmealId) {
        log.info("回显套餐信息");
        SetmealDto setmealDto = setmealService.getByIdWithSetmealDish(setmealId);
        return R.success(setmealDto);
    }
}
