package com.ewing.controller;

import Page.PageRespDto;
import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.annotation.RequiresPermission;
import com.ewing.domain.dto.req.LogReqDto;
import com.ewing.domain.dto.resp.LogRespDto;
import com.ewing.service.LogTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-11-22:37
 * @Description:
 */
@RestController
@Tag(name = "日志管理模块", description = "日志管理接口")
@RequestMapping(ApiRouterConstant.LOG_URL_PREFIX)
@RequiredArgsConstructor
public class LogController {

    private final LogTableService logTableService;

    @GetMapping("/recording")
    @RequiresPermission("MANAGE_LOGS")
    @MyLog(title = "日志模块", content = "查询用户日志")
    public ResultPage<List<LogRespDto>> recording(){
        return logTableService.recording();
    }


//    @PostMapping("/all_logs")
//    @RequiresPermission("MANAGE_LOGS")
//    @MyLog(title = "日志模块", content = "获取所有日志")
//    public ResultPage<PageRespDto<LogRespDto>> getAllLogs(@RequestBody @Validated LogReqDto logReqDto){
//        return logTableService.getAllLogs(logReqDto);
//    }
}
