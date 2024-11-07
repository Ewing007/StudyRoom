package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import Utils.SnowUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.dto.MessageByAdminDto;
import com.ewing.domain.dto.MessageDto;
import com.ewing.domain.dto.UserDto;
import com.ewing.domain.dto.req.MessageAllByUserReqDto;
import com.ewing.domain.dto.req.MessageByAdminReqDto;
import com.ewing.domain.dto.req.MessageReplyReqDto;
import com.ewing.domain.dto.req.MessageReqDto;
import com.ewing.domain.entity.MessageTable;
import com.ewing.manager.RedisCache;
import com.ewing.mapper.MessageTableMapper;
import com.ewing.mapper.UserTableMapper;
import com.ewing.service.MessageTableService;
import constant.CacheConstant;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import context.UserInfoContextHandler;
import lombok.RequiredArgsConstructor;
import Exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author ewing
* @description 针对表【message_table(留言表)】的数据库操作Service实现
* @createDate 2024-10-14 12:24:27
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageTableServiceImpl extends ServiceImpl<MessageTableMapper, MessageTable>
    implements MessageTableService {

    private final MessageTableMapper messageMapper;


    private final UserTableMapper userTableMapper;

    private final RedisCache redisCache;

    @Override
    public ResultPage<MessageDto> post(MessageReqDto messageReqDto) {
        String userId = UserInfoContextHandler.getUserContext().getUserId();
        UserDto user = (UserDto)redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);
        MessageTable message = new MessageTable();
        String id = SnowUtils.getSnowflakeNextIdStr();
        message.setMessageId(id);
        message.setTopMessageId(id);
        message.setContent(messageReqDto.getContent());
        message.setUserId(userId);
        message.setUserName(user.getUserName());
        message.setUserAvatar(user.getAvatar());
        message.setStatus(SystemConfigConstant.MESSAGE_STATUS_UNDELETED);
        message.setCreateTime(new DateTime());
        message.setUpdateTime(new DateTime());
        boolean status = this.save(message);
        MessageDto messageDto = BeanUtil.copyProperties(message, MessageDto.class);
        return ResultPage.SUCCESS(messageDto);
    }

    @Override
    public ResultPage<MessageDto> reply(MessageReplyReqDto messageReplyReqDto) throws BusinessException {
        //检查留言是否被删除
        log.info("检查留言是否被删除:{}", messageReplyReqDto.getReplyTo());
        MessageTable message = this.getOne(new QueryWrapper<MessageTable>().eq("message_id", messageReplyReqDto.getReplyTo()));
        //留言不存在或已被删除
        if(ObjectUtil.isNull(message) || SystemConfigConstant.MESSAGE_STATUS_DELETED.equals(message.getStatus())) {
//            throw new BusinessException(ErrorEnum.MESSAGE_NOT_EXIT);
            return ResultPage.FAIL(ErrorEnum.MESSAGE_NOT_EXIT);
        }

        String userId = UserInfoContextHandler.getUserContext().getUserId();
        UserDto userInfo = (UserDto) redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);
        MessageTable messageTable = new MessageTable();
        messageTable.setMessageId(SnowUtils.getSnowflakeNextIdStr());
        messageTable.setTopMessageId(message.getTopMessageId());
        messageTable.setContent(messageReplyReqDto.getContent());
        messageTable.setUserId(userId);
        messageTable.setReplyToUserId(messageReplyReqDto.getReplyToUserId());
        messageTable.setReplyToUserName(messageReplyReqDto.getReplyToUserName());
        messageTable.setUserAvatar(userInfo.getAvatar());
        messageTable.setUserName(userInfo.getUserName());
        messageTable.setReplyTo(messageReplyReqDto.getReplyTo());
        messageTable.setStatus(SystemConfigConstant.MESSAGE_STATUS_UNDELETED);
        messageTable.setCreateTime(new DateTime());
        messageTable.setUpdateTime(new DateTime());
        this.save(messageTable);
        MessageDto messageResp = BeanUtil.copyProperties(messageTable, MessageDto.class);
        return ResultPage.SUCCESS(messageResp);
    }

//    @Override
//    public ResultPage<List<MessageDto>> getRepliesByMessageId(Long messageId) {
//        return ResultPage.SUCCESS(messageMapper.getRepliesByMessageId(messageId));
//    }

//    @Override
//    public ResultPage<List<MessageDto>> getAllMessages() {
//        log.info("查询所有留言:{}",messageMapper.getRootMessages().toString());
//        return ResultPage.SUCCESS(messageMapper.getRootMessages());
//    }


    public ResultPage<List<MessageDto>> getAllMessages() {
        List<MessageTable> rootMessages = messageMapper.getRootMessages();
        List<MessageTable> messageDtos = addReplyDetails(rootMessages);
        List<MessageDto> finlandMessages = BeanUtil.copyToList(messageDtos, MessageDto.class);
        return ResultPage.SUCCESS(finlandMessages);
    }



//    public ResultPage<List<MessageDto>> getRepliesByMessageId(Long messageId) {
//        List<MessageTable> replies = messageMapper.getRepliesByMessageId(messageId);
//        List<MessageTable> messageTables = addReplyDetails(replies);
//        List<MessageDto> messageDtos = BeanUtil.copyToList(messageTables, MessageDto.class);
//        return ResultPage.SUCCESS(messageDtos);
//    }

    private List<MessageTable> addReplyDetails(List<MessageTable> messages) {
        return messages.stream().map(message -> {
            if (message.getReplyTo() != null) {
                MessageTable replyToMessage = messageMapper.getRepliesByMessageId(message.getReplyTo()).stream()
                        .filter(reply -> reply.getMessageId().equals(message.getReplyTo()))
                        .findFirst()
                        .orElse(null);
                if (replyToMessage != null) {
                    message.setReplyToUserId(replyToMessage.getUserId());
                    message.setReplyToUserName(replyToMessage.getUserName());
//                    message.setReplyToUserAvatar(replyToMessage.getUserAvatar());
                }
            }
            return message;
        }).collect(Collectors.toList());
    }

    public ResultPage<List<MessageDto>> getRepliesByMessageId(String messageId) {
        // 获取顶级留言
        MessageTable topMessage = messageMapper.getMessageById(messageId);
        if (topMessage == null) {
            return ResultPage.FAIL(ErrorEnum.MESSAGE_NOT_EXIT);
        }

        // 获取顶级留言下的所有回复，排除顶级留言本身
        List<MessageTable> allReplies = messageMapper.getRepliesByTopMessageIdAndExcludeTopMessage(topMessage.getTopMessageId(), topMessage.getMessageId());


        log.info("获取顶级留言下的所有回复，排除顶级留言本身:{}", allReplies.toString());
        log.info("获取顶级留言下的所有回复，排除顶级留言本身条数:{}", allReplies.size());
        // 构建树形结构
//        List<MessageDto> messageDtos = buildTree(allReplies, messageId);

        // 构建扁平列表
        List<MessageDto> flatMessages = buildFlatList(allReplies);

        log.info("构建树形结构条数:{}", flatMessages.size());
        return ResultPage.SUCCESS(flatMessages);
    }

    private List<MessageDto> buildTree(List<MessageTable> allReplies, String rootId) {
        Map<String, MessageDto> messageDtoMap = new HashMap<>();
        List<MessageDto> rootMessages = new ArrayList<>();

        for (MessageTable message : allReplies) {
            MessageDto messageDto = BeanUtil.copyProperties(message, MessageDto.class);
            messageDtoMap.put(message.getMessageId(), messageDto);
        }

        for (MessageDto messageDto : messageDtoMap.values()) {
            if (messageDto.getReplyTo() == null || messageDto.getReplyTo().equals(rootId)) {
                rootMessages.add(messageDto);
            } else {
                MessageDto parentDto = messageDtoMap.get(messageDto.getReplyTo());
                if (parentDto != null) {
                    if (parentDto.getChildren() == null) {
                        parentDto.setChildren(new ArrayList<>());
                    }
                    parentDto.getChildren().add(messageDto);
                }
            }
        }
        return rootMessages;
    }

    private List<MessageDto> buildFlatList(List<MessageTable> allReplies) {
        List<MessageDto> flatList = new ArrayList<>();
        for (MessageTable message : allReplies) {
            MessageDto messageDto = BeanUtil.copyProperties(message, MessageDto.class);
            flatList.add(messageDto);
        }
        return flatList;
    }


    @Override
    public ResultPage<Void> deleteMessage(String messageId) {
        String userId = UserInfoContextHandler.getUserContext().getUserId();
        UpdateWrapper<MessageTable> updateWrapper = new UpdateWrapper<MessageTable>()
                .eq("message_id", messageId)
                .eq("user_id", userId);
        updateWrapper.set("status", SystemConfigConstant.MESSAGE_STATUS_DELETED);
        updateWrapper.set("del_flag", SystemConfigConstant.MESSAGE_DELETED_FLAG);
        updateWrapper.set("update_time", new DateTime());
        boolean status = this.update(updateWrapper);
        return status ? ResultPage.SUCCESS(ErrorEnum.DELETE_MESSAGE_SUCCESS) : ResultPage.FAIL(ErrorEnum.DELETE_MESSAGE_FAILURE);
    }

    @Override
    public ResultPage<PageRespDto<MessageDto>> getMessagesByUserId(MessageAllByUserReqDto messageAllByUserReqDto) {
        String userId = UserInfoContextHandler.getUserContext().getUserId();
        Page<MessageTable> page = new Page<>(messageAllByUserReqDto.getPageNum(), messageAllByUserReqDto.getPageSize());
        Page<MessageTable> messageTablePage = messageMapper.selectPage(page, new QueryWrapper<MessageTable>()
                .eq("user_id", userId)
                .eq("del_flag", SystemConfigConstant.MESSAGE_UNDELETED_FLAG)
                .eq("status", SystemConfigConstant.MESSAGE_STATUS_UNDELETED)
                .orderByDesc("create_time"));
        List<MessageTable> records = messageTablePage.getRecords();
        List<MessageDto> messageDtos = BeanUtil.copyToList(records, MessageDto.class);
        PageRespDto<MessageDto> messageDtoPageRespDto = PageRespDto.of(messageAllByUserReqDto.getPageNum(), messageAllByUserReqDto.getPageSize(), messageTablePage.getTotal(), messageDtos);
        return ResultPage.SUCCESS(messageDtoPageRespDto);
    }

    @Override
    public ResultPage<Void> deleteMessageByAdmin(String messageId) {
        UpdateWrapper<MessageTable> updateWrapper = new UpdateWrapper<MessageTable>()
                .eq("message_id", messageId);
        MessageTable messageTable = new MessageTable();
        messageTable.setStatus(SystemConfigConstant.MESSAGE_STATUS_DELETED);
        messageTable.setDelFlag(SystemConfigConstant.MESSAGE_DELETED_FLAG);

        this.update(messageTable, updateWrapper);
        return ResultPage.SUCCESS(ErrorEnum.DELETE_MESSAGE_SUCCESS);
    }

    @Override
    public ResultPage<PageRespDto<MessageByAdminDto>> getAllMessagesByAdmin(MessageByAdminReqDto messageByAdminReqDto) {
        Page<MessageTable> page = new Page<>(messageByAdminReqDto.getPageNum(), messageByAdminReqDto.getPageSize());
        Page<MessageTable> messageTablePage = messageMapper.selectPage(page, null);
        List<MessageTable> records = messageTablePage.getRecords();
        List<MessageByAdminDto> messageByAdminDtos = BeanUtil.copyToList(records, MessageByAdminDto.class);
        PageRespDto<MessageByAdminDto> messageByAdminDtoPageRespDto = PageRespDto.of(messageByAdminReqDto.getPageNum(), messageByAdminReqDto.getPageSize(), messageTablePage.getTotal(), messageByAdminDtos);
        return ResultPage.SUCCESS(messageByAdminDtoPageRespDto);
    }
}




