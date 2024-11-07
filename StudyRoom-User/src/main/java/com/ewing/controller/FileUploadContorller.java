package com.ewing.controller;

import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.service.FileUploadService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Ewing
 * @Date: 2024-10-28-14:08
 * @Description:
 */
@RestController
@Tag(name = "文件上传模块", description = "文件上传模块")
@RequestMapping(ApiRouterConstant.FILE_UPLOAD_URL_PREFIX)
@RequiredArgsConstructor
public class FileUploadContorller {

    private final FileUploadService fileUploadService;
    @Operation(summary = "用户头像上传")
    @MyLog(title = "文件上传模块", content = "用户头像上传")
    @PostMapping("/user_avatar")
    public ResultPage<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadAvatar(file);
    }

    @Operation(summary = "自习室图像上传")
    @MyLog(title = "文件上传模块", content = "自习室图像上传")
    @PostMapping("/studyroom_image")
    public ResultPage<String> uploadStudyRoomImage(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadStudyRoomImage(file);
    }

    @Operation(summary = "失物招领图像上传")
    @MyLog(title = "文件上传模块", content = "失物招领图像上传")
    @PostMapping("/lost_found_image")
    public ResultPage<String> uploadLostAndFoundImage(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadLostAndFoundImage(file);
    }
}
