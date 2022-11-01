package com.wdl.reggie.dto;


import com.wdl.reggie.entity.Setmeal;
import com.wdl.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
