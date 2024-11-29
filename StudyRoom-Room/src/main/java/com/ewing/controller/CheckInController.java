package com.ewing.controller;

import Result.ResultPage;
import com.ewing.service.CheckInTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ewing
 * @Date: 2024-11-29-0:23
 * @Description:
 */
@Tag(name = "自习室预约管理模块", description = "自习室预约管理接口")
@RestController
@RequestMapping(ApiRouterConstant.QR_CODE_URL_PREFIX)
@RequiredArgsConstructor
@Slf4j
public class CheckInController {

    private final CheckInTableService checkInTableService;
    @GetMapping("/check-in")
    public ResultPage<Void> checkIn(
            @RequestParam String encryptedUserId,
            @RequestParam String encryptedReservationId,
            @RequestParam String expirationTime) throws Exception {
        return checkInTableService.checkIn(encryptedUserId, encryptedReservationId, expirationTime);
    }
}
