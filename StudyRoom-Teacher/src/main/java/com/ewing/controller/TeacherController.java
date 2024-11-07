package com.ewing.controller;

import Result.ResultPage;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ewing
 * @Date: 2024-10-08-11:02
 * @Description:
 */
@RestController
@RequestMapping(ApiRouterConstant.TEACHER_URL_PREFIX)
@Tag(name = "教师服务控制", description = "教师接口")
public class TeacherController {

    @GetMapping("/test")
    @Operation(summary = "接口测试")
    public ResultPage<String> test(){
        return ResultPage.SUCCESS("hello teacher");
    }
}
