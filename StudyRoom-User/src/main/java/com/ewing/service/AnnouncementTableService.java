package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.AnnouncementByAdminDto;
import com.ewing.domain.dto.AnnouncementDto;
import com.ewing.domain.dto.req.AnnouncementCreateReqDto;
import com.ewing.domain.dto.req.AnnouncementReqDto;
import com.ewing.domain.dto.req.AnnouncementUpdateReqDto;
import com.ewing.domain.entity.AnnouncementTable;

import java.util.List;


/**
* @author ewing
* @description 针对表【announcement_table(公告表)】的数据库操作Service
* @createDate 2024-10-14 22:45:15
*/
public interface AnnouncementTableService extends IService<AnnouncementTable> {

    ResultPage<List<AnnouncementDto>> getAllAnnouncement();

    ResultPage<Void> updateAnnouncement(AnnouncementUpdateReqDto announcementUpdateReqDto);

    ResultPage<Void> createAnnouncement(AnnouncementCreateReqDto announcementCreateReqDto);

    ResultPage<PageRespDto<AnnouncementByAdminDto>> getAnnouncementsByAdmin(AnnouncementReqDto announcementReqDto);
}
