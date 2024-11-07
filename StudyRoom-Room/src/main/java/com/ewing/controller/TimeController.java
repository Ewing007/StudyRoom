package com.ewing.controller;

import Result.ResultPage;
import com.ewing.domain.dto.TimeSlotDto;
import com.ewing.service.TimeSlotTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-11-02-17:42
 * @Description:
 */
@RestController
@Tag(name = "时间段管理模块", description = "时间段管理接口")
@RequestMapping(ApiRouterConstant.TIME_URL_PREFIX)
@RequiredArgsConstructor
public class TimeController {

    private final TimeSlotTableService timeService;
    @GetMapping("/all")
    public ResultPage<List<TimeSlotDto>> getAllTimeSlots() {
        return timeService.getAllTimeSlots();
        }
}
