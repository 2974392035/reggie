package com.wdl.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author:wudl
 * @creat 2022/10/8 14:33
 * @name reggie
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan(basePackages = {"com.wdl.reggie.filter"})
@EnableTransactionManagement//插入数据时要更新多个表
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
    }
}
