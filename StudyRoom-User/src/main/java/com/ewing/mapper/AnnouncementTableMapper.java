package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.dto.AnnouncementDto;
import com.ewing.domain.entity.AnnouncementTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
* @author ewing
* @description 针对表【announcement_table(公告表)】的数据库操作Mapper
* @createDate 2024-10-14 22:45:15
* @Entity generator.domain.AnnouncementTable
*/
@Mapper
public interface AnnouncementTableMapper extends BaseMapper<AnnouncementTable> {

    List<AnnouncementDto> selectAllAnnouncements();

    /**
     * 查询所有已发布且已过期的公告
     */
    List<AnnouncementTable> selectActiveAnnouncements();

    void updateAnnouncement(AnnouncementTable announcement);

    /**
     * 更新公告状态为已过期
     */
    void updateAnnouncementStatusToExpired(@Param("id") Long id);
}




