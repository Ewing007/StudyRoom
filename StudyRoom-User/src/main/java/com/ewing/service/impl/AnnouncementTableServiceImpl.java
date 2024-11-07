package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.dto.AnnouncementByAdminDto;
import com.ewing.domain.dto.AnnouncementDto;
import com.ewing.domain.dto.req.AnnouncementCreateReqDto;
import com.ewing.domain.dto.req.AnnouncementReqDto;
import com.ewing.domain.dto.req.AnnouncementUpdateReqDto;
import com.ewing.domain.entity.AnnouncementTable;
import com.ewing.domain.entity.LogTable;
import com.ewing.mapper.AnnouncementTableMapper;

import com.ewing.service.AnnouncementTableService;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import context.UserInfoContextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ewing
* @description 针对表【announcement_table(公告表)】的数据库操作Service实现
* @createDate 2024-10-14 22:45:15
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementTableServiceImpl extends ServiceImpl<AnnouncementTableMapper, AnnouncementTable>
    implements AnnouncementTableService {

    private final AnnouncementTableMapper announcementTableMapper;
    @Override
    public ResultPage<List<AnnouncementDto>> getAllAnnouncement() {
        List<AnnouncementDto> announcementDtoList = announcementTableMapper.selectAllAnnouncements();
        return ResultPage.SUCCESS(announcementDtoList);
    }

    @Override
    public ResultPage<Void> updateAnnouncement(AnnouncementUpdateReqDto announcementUpdateReqDto) {
        AnnouncementTable announcementTable = new AnnouncementTable();
        announcementTable.setTitle(announcementUpdateReqDto.getTitle());
        announcementTable.setAuthorName(announcementUpdateReqDto.getAuthorName());
        announcementTable.setContent(announcementUpdateReqDto.getContent());
        announcementTable.setStatus(announcementUpdateReqDto.getStatus());
        announcementTable.setId(announcementUpdateReqDto.getId());
        announcementTable.setStartTime(announcementUpdateReqDto.getStartTime());
        announcementTable.setEndTime(announcementUpdateReqDto.getEndTime());
        announcementTable.setCreatedTime(announcementUpdateReqDto.getCreatedTime());
        announcementTable.setUpdateTime(new DateTime());
        announcementTableMapper.updateAnnouncement(announcementTable);
        return ResultPage.SUCCESS();
    }

    @Override
    public ResultPage<Void> createAnnouncement(AnnouncementCreateReqDto announcementCreateReqDto) {
        AnnouncementTable announcementTable = new AnnouncementTable();
        announcementTable.setAuthorId(UserInfoContextHandler.getUserContext().getUserId());
        announcementTable.setTitle(announcementCreateReqDto.getTitle());
        announcementTable.setAuthorName(announcementCreateReqDto.getAuthorName());
        announcementTable.setContent(announcementCreateReqDto.getContent());
        announcementTable.setStatus(SystemConfigConstant.ANNOUNCEMENT_STATUS_PUBLISHED);
        announcementTable.setCreatedTime(announcementCreateReqDto.getCreatedTime());
        announcementTable.setStartTime(announcementCreateReqDto.getStartTime());
        announcementTable.setEndTime(announcementCreateReqDto.getEndTime());
        boolean save = this.save(announcementTable);
        return save ? ResultPage.SUCCESS(ErrorEnum.ANNOUNCEMENT_POST_SUCCESS) : ResultPage.FAIL(ErrorEnum.ANNOUNCEMENT_POST_FAILURE);
    }


    @Override
    public ResultPage<PageRespDto<AnnouncementByAdminDto>> getAnnouncementsByAdmin(AnnouncementReqDto announcementReqDto) {
        log.info("announcementReqDto参数: {}", announcementReqDto.toString());
        if (announcementReqDto.isFetchAll()) {
            log.info("获取所有公告");
            // 如果 fetchAll 为 true，获取所有公告
            List<AnnouncementTable> announcements = this.list();
            List<AnnouncementByAdminDto> announcementByAdminDtos = BeanUtil.copyToList(announcements, AnnouncementByAdminDto.class);
            log.info("announcements: {}", announcements);
            PageRespDto<AnnouncementByAdminDto> pageRespDto = PageRespDto.of(announcementReqDto.getPageNum(), announcementReqDto.getPageSize(), announcements.size(), announcementByAdminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        } else {
            // 如果 fetchAll 为 false，按分页查询
            Page<AnnouncementTable> page = new Page<>(announcementReqDto.getPageNum(), announcementReqDto.getPageSize());
            Page<AnnouncementTable> announcementPage = announcementTableMapper.selectPage(page, null);
            List<AnnouncementTable> announcements = announcementPage.getRecords();
            List<AnnouncementByAdminDto> announcementByAdminDtos = BeanUtil.copyToList(announcements, AnnouncementByAdminDto.class);
            PageRespDto<AnnouncementByAdminDto> pageRespDto = PageRespDto.of(announcementReqDto.getPageNum(), announcementReqDto.getPageSize(), announcementPage.getTotal(), announcementByAdminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        }
    }
}




