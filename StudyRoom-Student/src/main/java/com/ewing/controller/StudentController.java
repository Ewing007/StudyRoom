package com.ewing.controller;

import Result.ResultPage;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ewing
 * @Date: 2024-09-30-23:52
 * @Description:
 */
@Tag(name = "学生服务控制", description = "学生接口")
@RestController
@RequestMapping(ApiRouterConstant.STUDENT_URL_PREFIX)
@Slf4j
public class StudentController {

    @GetMapping("test")
    @Operation(summary = "测试接口")
    public ResultPage<String> test1() {
        return  ResultPage.SUCCESS("test success");
    }


}
