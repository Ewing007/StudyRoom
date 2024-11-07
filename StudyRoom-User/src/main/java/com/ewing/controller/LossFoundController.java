package com.ewing.controller;

import Page.PageRespDto;
import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.annotation.RequiresPermission;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.LostFoundDto;
import com.ewing.domain.dto.resp.LostFoundByAdminDto;
import com.ewing.service.LostFoundTableService;
import constant.ApiRouterConstant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Exception.BusinessException;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-16:51
 * @Description:
 */
@RestController
@Tag(name = "失物招领管理模块", description = "失物招领管理接口")
@RequestMapping(ApiRouterConstant.LOSS_FOUND_URL_PREFIX)
@RequiredArgsConstructor
public class LossFoundController {

    private final LostFoundTableService lostFoundTableService;
    @Operation(summary = "发布失物招领接口")
    @MyLog(title = "失物招领管模块", content = "用户发布失物招领")
    @PostMapping("/post")
//    @RequiresPermission("USER_LOGGED_IN")
    public ResultPage<Void> post(@RequestBody @Validated LostFoundReqDto lostFoundReqDto) {
        return lostFoundTableService.post(lostFoundReqDto);
    }

    @Operation(summary = "查看我的失物招领接口")
    @MyLog(title = "失物招领管模块", content = "用户查看自己发布失物招领")
    @PostMapping("/view_persona")
//    @RequiresPermission("USER_LOGGED_IN")
    public ResultPage<PageRespDto<LostFoundDto>> viewPost(@RequestBody @Validated LostFoundByUserReqDto lostFoundByUserId) {
        return lostFoundTableService.viewPersona(lostFoundByUserId);
    }

//    @Operation(summary = "查看所有失物招领接口")
//    @MyLog(title = "失物招领管模块", content = "用户所有发布失物招领")
//    @GetMapping("/all")
////    @RequiresPermission("USER_LOGGED_IN")
//    public ResultPage<List<LostFoundDto>> all() {
//        return lostFoundTableService.all();
//    }

//    @Operation(summary = "获取某条失物招领接口")
//    @MyLog(title = "失物招领管模块", content = "获取某条失物招领接口")
//    @GetMapping("/{id}/lost_found")
////    @RequiresPermission("USER_LOGGED_IN")
//    public ResultPage<LostFoundDto> getAllByLostId(@PathVariable("id") Long LostId) throws BusinessException {
//        return lostFoundTableService.getAllByLostId(LostId);
//    }

    @Operation(summary = "管理员管理失物招领接口")
    @MyLog(title = "失物招领管模块", content = "管理员管理失物招领接口")
    @PostMapping("/manager")
//    @RequiresPermission("MANAGE_LOST_FOUND")
    public ResultPage<Void> managerByAdmin(@RequestBody @Validated LostFoundAdminReqDto lostFoundAdminReqDto) {
        return lostFoundTableService.managerByAdmin(lostFoundAdminReqDto);
    }

    @Operation(summary = "管理个人失物招领接口")
    @MyLog(title = "失物招领管模块", content = "管理个人失物招领接口")
    @PostMapping("/persona")
//    @RequiresPermission("USER_LOGGED_IN")
    public ResultPage<LostFoundDto> persona(@RequestBody @Validated LostFoundPersonaReqDto lostFoundPersonaReqDto) {
        return lostFoundTableService.persona(lostFoundPersonaReqDto);
    }

    @Operation(summary = "管理员获取所有失物招领接口")
    @MyLog(title = "失物招领管模块", content = "管理员获取所有失物招领接口")
    @DeleteMapping("/get_allLosts_ByAdmin")
//    @RequiresPermission("MANAGE_LOST_FOUND")
    public ResultPage<PageRespDto<LostFoundByAdminDto>> getAllloststByAdmin(@RequestBody @Validated LostFoundAllByAdminReqDto lostFoundAllByAdminReqDto) {
        return lostFoundTableService.getAllLoststByAdmin(lostFoundAllByAdminReqDto);
    }
}
