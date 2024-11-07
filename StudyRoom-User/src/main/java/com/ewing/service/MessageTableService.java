package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.MessageByAdminDto;
import com.ewing.domain.dto.MessageDto;
import com.ewing.domain.dto.req.MessageAllByUserReqDto;
import com.ewing.domain.dto.req.MessageByAdminReqDto;
import com.ewing.domain.dto.req.MessageReplyReqDto;
import com.ewing.domain.dto.req.MessageReqDto;
import com.ewing.domain.entity.MessageTable;
import Exception.BusinessException;

import java.util.List;

/**
* @author ewing
* @description 针对表【message_table(留言表)】的数据库操作Service
* @createDate 2024-10-14 12:24:27
*/
public interface MessageTableService extends IService<MessageTable> {

    ResultPage<MessageDto> post(MessageReqDto messageReqDto);

    ResultPage<MessageDto> reply(MessageReplyReqDto messageReplyReqDto) throws BusinessException;

    ResultPage<List<MessageDto>> getRepliesByMessageId(String messageId);

    ResultPage<List<MessageDto>> getAllMessages();

    ResultPage<Void> deleteMessage(String messageId);

    ResultPage<PageRespDto<MessageDto>> getMessagesByUserId(MessageAllByUserReqDto messageAllByUserReqDto);

    ResultPage<Void> deleteMessageByAdmin(String messageId);

    ResultPage<PageRespDto<MessageByAdminDto>> getAllMessagesByAdmin(MessageByAdminReqDto messageByAdminReqDto);
}
