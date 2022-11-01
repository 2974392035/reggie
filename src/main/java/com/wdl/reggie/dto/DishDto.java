package com.wdl.reggie.dto;

import com.wdl.reggie.entity.Dish;
import com.wdl.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wudl
 * @creat 2022/10/18 21:21
 * @name reggie
 */


@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
