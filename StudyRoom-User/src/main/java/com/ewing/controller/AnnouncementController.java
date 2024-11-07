package com.ewing.controller;

import Page.PageRespDto;
import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.annotation.RequiresPermission;
import com.ewing.domain.dto.AnnouncementByAdminDto;
import com.ewing.domain.dto.AnnouncementDto;
import com.ewing.domain.dto.req.AnnouncementCreateReqDto;
import com.ewing.domain.dto.req.AnnouncementReqDto;
import com.ewing.domain.dto.req.AnnouncementUpdateReqDto;
import com.ewing.service.AnnouncementTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-23:10
 * @Description:
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "公告管理模块", description = "公告接口")
@RequestMapping(ApiRouterConstant.ANNOUNCEMENT_FOUND_URL_PREFIX)
public class AnnouncementController {

    private final AnnouncementTableService announcementService;

    @Operation(summary = "创建公告")
    @MyLog(title = "公告模块", content = "创建公告")
    @PostMapping("/create")
//    @RequiresPermission("MANAGE_ANNOUNCEMENT")
    public ResultPage<Void> createAnnouncement(@RequestBody @Validated AnnouncementCreateReqDto announcementCreateReqDto) {
        return announcementService.createAnnouncement(announcementCreateReqDto);
    }

    @Operation(summary = "获取所有公告")
    @GetMapping("/all")
    public ResultPage<List<AnnouncementDto>> getAllAnnouncement() {
        return announcementService.getAllAnnouncement();
    }

//    @Operation(summary = "获取所有公告")
//    @MyLog(title = "管理员模块", content = "获取所有公告")
//    @PostMapping("/get_allAnnouncements_by_admin")
//    public ResultPage<PageRespDto<AnnouncementByAdminDto>> getAnnouncementsByAdmin(@RequestBody @Validated AnnouncementReqDto announcementReqDto) {
//        return announcementService.getAnnouncementsByAdmin(announcementReqDto);
//    }

    @Operation(summary = "更新公告")
    @MyLog(title = "公告模块", content = "更新公告")
    @PostMapping("/update")
//    @RequiresPermission("MANAGE_ANNOUNCEMENT")
    public ResultPage<Void> updateAnnouncement(@RequestBody @Validated AnnouncementUpdateReqDto announcementUpdateReqDto) {
        return announcementService.updateAnnouncement(announcementUpdateReqDto);
    }
}
