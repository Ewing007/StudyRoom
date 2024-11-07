package com.ewing.mapper;
import com.ewing.domain.dto.MessageDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.entity.MessageTable;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;


import java.util.Collection;
import java.util.List;


/**
* @author ewing
* @description 针对表【message_table(留言表)】的数据库操作Mapper
* @createDate 2024-10-14 12:24:27
* @Entity generator.domain.MessageTable
*/
public interface MessageTableMapper extends BaseMapper<MessageTable> {

//    /**
//     * 获取某条留言的所有回复，并携带发布者的用户名和角色
//     *
//     * @param messageId 被回复的留言ID
//     * @return 回复的留言列表
//     */
//    List<MessageDto> getRepliesByMessageId(@Param("messageId") Long messageId);
//
//    /**
//     * 获取所有根留言（非回复），并携带发布者的用户名和角色
//     *
//     * @return 根留言列表
//     */
//    List<MessageDto> getRootMessages();

//
//    /**
//     * 获取某条留言的所有回复，并携带发布者的用户名和头像
//     *
//     * @param messageId 被回复的留言ID
//     * @return 回复的留言列表
//     */
//    @ResultMap("MessageDtoResultMap")
//    List<MessageTable> getRepliesByMessageId(@Param("messageId") Long messageId);

    /**
     * 获取所有根留言（非回复），并携带发布者的用户名和头像
     *
     * @return 根留言列表
     */
    @ResultMap("MessageDtoResultMap")
    List<MessageTable> getRootMessages();

    MessageTable getMessageById(String id);
    List<MessageTable> getRepliesByMessageId(String messageId);
    List<MessageTable> getRepliesByTopMessageId(String topMessageId);


    // 获取顶级留言下的所有回复，排除顶级留言本身
    List<MessageTable> getRepliesByTopMessageIdAndExcludeTopMessage(@Param("topMessageId") String topMessageId, @Param("excludeId") String excludeId);

}




