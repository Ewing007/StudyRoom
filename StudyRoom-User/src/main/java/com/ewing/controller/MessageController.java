package com.ewing.controller;

import Page.PageRespDto;
import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.domain.dto.MessageByAdminDto;
import com.ewing.domain.dto.MessageDto;
import com.ewing.domain.dto.req.MessageAllByUserReqDto;
import com.ewing.domain.dto.req.MessageByAdminReqDto;
import com.ewing.domain.dto.req.MessageReplyReqDto;
import com.ewing.domain.dto.req.MessageReqDto;
import com.ewing.service.MessageTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import Exception.BusinessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-13:30
 * @Description:
 */
@RestController
@Tag(name = "留言管理模块", description = "留言管理接口")
@RequestMapping(ApiRouterConstant.MESSAGE_URL_PREFIX)
@RequiredArgsConstructor
public class MessageController {

    private final MessageTableService messageService;

    @Operation(summary = "发表留言接口")
    @MyLog(title = "留言模块", content = "用户发表留言")
    @PostMapping("/post")
    public ResultPage<MessageDto> post(@RequestBody @Validated MessageReqDto messageReqDto) {
        return messageService.post(messageReqDto);
    }

    @Operation(summary = "回复留言接口")
    @MyLog(title = "留言模块", content = "用户回复留言")
    @PostMapping("/reply")
    public ResultPage<MessageDto> reply(@RequestBody @Validated MessageReplyReqDto messageReplyReqDto) throws BusinessException {
        return messageService.reply(messageReplyReqDto);
    }

    @Operation(summary = "获取某条留言的所有回复接口")
    @MyLog(title = "留言模块", content = "获取某条留言的所有回复")
    @GetMapping("/replies/{messageId}")
    public ResultPage<List<MessageDto>> getRepliesByMessageId(@PathVariable("messageId") String messageId) {
        return messageService.getRepliesByMessageId(messageId);
    }

    @Operation(summary = "获取所有留言接口")
    @MyLog(title = "留言模块", content = "获取所有留言")
    @GetMapping("/all")
    public ResultPage<List<MessageDto>> getAllMessages() {
        return messageService.getAllMessages();
    }

    @Operation(summary = "某个用户的所有留言接口")
    @MyLog(title = "留言模块", content = "某个用户的所有留言")
    @PostMapping("/user/getMessagse")
    public ResultPage<PageRespDto<MessageDto>> getMessagesByUserId(@RequestBody @Validated MessageAllByUserReqDto messageAllByUserReqDto) {
        return messageService.getMessagesByUserId(messageAllByUserReqDto);
    }

    @Operation(summary = "删除留言接口")
    @MyLog(title = "留言模块", content = "删除留言")
    @DeleteMapping("/delete/{messageId}")
    public ResultPage<Void> deleteMessage(@PathVariable("messageId") String messageId) {
        return messageService.deleteMessage(messageId);
    }

//
//    @Operation(summary = "管理员删除留言接口")
//    @MyLog(title = "留言模块", content = "管理员删除留言")
//    @DeleteMapping("/delete_by_admin/{messageId}")
//    public ResultPage<Void> deleteMessageByAdmin(@PathVariable("messageId") String messageId) {
//        return messageService.deleteMessageByAdmin(messageId);
//    }
//
//    @Operation(summary = "管理员获取所有留言接口")
//    @MyLog(title = "留言模块", content = "管理员获取所有留言")
//    @GetMapping("/admin/all")
//    public ResultPage<PageRespDto<MessageByAdminDto>> getAllMessagesByAdmin(@RequestBody @Validated MessageByAdminReqDto messageByAdminReqDto) {
//        return messageService.getAllMessagesByAdmin(messageByAdminReqDto);
//    }
}
