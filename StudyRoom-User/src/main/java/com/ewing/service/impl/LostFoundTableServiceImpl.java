package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.LostFoundDto;
import com.ewing.domain.dto.resp.LostFoundByAdminDto;
import com.ewing.domain.entity.LostFoundTable;
import com.ewing.mapper.LostFoundTableMapper;
import com.ewing.service.LostFoundTableService;
import constant.DataBaseConstant;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import context.UserInfoContextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import Exception.BusinessException;
import java.util.List;

/**
* @author ewing
* @description 针对表【lost_found_table(失物遗失招领表)】的数据库操作Service实现
* @createDate 2024-10-14 16:46:53
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class LostFoundTableServiceImpl extends ServiceImpl<LostFoundTableMapper, LostFoundTable>
    implements LostFoundTableService {

    private final LostFoundTableMapper lostFoundTableMapper;
    @Override
    public ResultPage<Void> post(LostFoundReqDto lostFoundReqDto) {
        LostFoundTable lostFoundTable = new LostFoundTable();
        lostFoundTable.setUserId(UserInfoContextHandler.getUserContext().getUserId());
        lostFoundTable.setItemType(lostFoundReqDto.getItemType());
        lostFoundTable.setDescription(lostFoundReqDto.getDescription());
        lostFoundTable.setLocation(lostFoundReqDto.getLocation());
        lostFoundTable.setContactInfo(lostFoundReqDto.getContactInfo());
        lostFoundTable.setImageUrl(lostFoundReqDto.getImageUrl());
        lostFoundTable.setStatus(SystemConfigConstant.LOSTFOUND_STATUS_UNDISABLE);
        lostFoundTable.setDelIllegal(SystemConfigConstant.LOSTFOUND_UNILLEGAL);
        lostFoundTable.setCreateTime(new DateTime());
        lostFoundTable.setUpdateTime(new DateTime());
        boolean save = this.save(lostFoundTable);
        return save ? ResultPage.SUCCESS(ErrorEnum.LOSTFOUND_POST_SUCCESS) : ResultPage.FAIL(ErrorEnum.LOSTFOUND_POST_FAILURE);
    }

    @Override
    public ResultPage<PageRespDto<LostFoundDto>> viewPersona(LostFoundByUserReqDto lostFoundByUserId) {
//        List<LostFoundTableMapper> allLostFounds = lostFoundTableMapper.getAllLostFounds();
//        List<LostFoundDto> lostFoundDtos = BeanUtil.copyToList(allLostFounds, LostFoundDto.class);
        Page<LostFoundTable> lostFoundPage = new Page<>(lostFoundByUserId.getPageNum(), lostFoundByUserId.getPageSize());
        Page<LostFoundTable> lostFoundTablePage = lostFoundTableMapper.selectPage(lostFoundPage, null);
        List<LostFoundTable> records = lostFoundTablePage.getRecords();
        List<LostFoundDto> lostFoundDtoList = BeanUtil.copyToList(records, LostFoundDto.class);
        PageRespDto<LostFoundDto> pageRespDto = PageRespDto.of(lostFoundByUserId.getPageNum(), lostFoundByUserId.getPageSize(), lostFoundTablePage.getTotal(), lostFoundDtoList);
        return ResultPage.SUCCESS(pageRespDto);
    }

    @Override
    public ResultPage<List<LostFoundDto>> all() {
        List<LostFoundTableMapper> allLostFounds = lostFoundTableMapper.getAllLostFounds();
        List<LostFoundDto> lostFoundDtos = BeanUtil.copyToList(allLostFounds, LostFoundDto.class);
        return ResultPage.SUCCESS(lostFoundDtos);
    }

    @Override
    public ResultPage<LostFoundDto> getAllByLostId(Long lostId) throws BusinessException {
        LostFoundTable lostFoundTable = this.getById(lostId);
        if(SystemConfigConstant.LOSTFOUND_ILLEGAL.equals(lostFoundTable.getDelIllegal()) ||
           SystemConfigConstant.LOSTFOUND_DELETE.equals(lostFoundTable.getStatus())
        ) {
            throw new BusinessException(ErrorEnum.LOSTFOUND_POST_UNEXIT);
        }
        if(ObjectUtil.isNull(lostFoundTable)) {
            throw new BusinessException(ErrorEnum.LOSTFOUND_POST_UNEXIT);
        }
        LostFoundDto lostFoundDto = BeanUtil.copyProperties(lostFoundTable, LostFoundDto.class);
        return ResultPage.SUCCESS(lostFoundDto);
    }

    @Override
    public ResultPage<Void> managerByAdmin(LostFoundAdminReqDto lostFoundAdminReqDto) {
        QueryWrapper<LostFoundTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.LostFoundTable.COLUMN_ID, lostFoundAdminReqDto.getId())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());

        LostFoundTable lostFoundTable = new LostFoundTable();
        lostFoundTable.setStatus(lostFoundAdminReqDto.getStatus());
        lostFoundTable.setDelIllegal(lostFoundAdminReqDto.getDelIllegal());
        lostFoundTable.setIllegal(lostFoundAdminReqDto.getIllegal());
        lostFoundTable.setUpdateTime(new DateTime());
        lostFoundTableMapper.update(lostFoundTable, queryWrapper);
        return ResultPage.SUCCESS();
    }

    @Override
    public ResultPage<LostFoundDto> persona(LostFoundPersonaReqDto lostFoundPersonaReqDto) {
        QueryWrapper<LostFoundTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.LostFoundTable.COLUMN_ID, lostFoundPersonaReqDto.getId())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
        LostFoundTable lostFoundTable = new LostFoundTable();
        lostFoundTable.setUserId(UserInfoContextHandler.getUserContext().getUserId());
        lostFoundTable.setItemType(lostFoundPersonaReqDto.getItemType());
        lostFoundTable.setDescription(lostFoundPersonaReqDto.getDescription());
        lostFoundTable.setLocation(lostFoundPersonaReqDto.getLocation());
        lostFoundTable.setImageUrl(lostFoundPersonaReqDto.getImageUrl());
        lostFoundTable.setContactInfo(lostFoundPersonaReqDto.getContactInfo());
        lostFoundTable.setUpdateTime(new DateTime());
        lostFoundTable.setStatus(lostFoundPersonaReqDto.getStatus());
        lostFoundTableMapper.update(lostFoundTable,queryWrapper);
        LostFoundDto lostFoundDto = BeanUtil.copyProperties(lostFoundTable, LostFoundDto.class);
        return ResultPage.SUCCESS(lostFoundDto);
    }

    @Override
    public ResultPage<PageRespDto<LostFoundByAdminDto>> getAllLoststByAdmin(LostFoundAllByAdminReqDto lostFoundAllByAdminReqDto) {
        log.info("lostFoundAllByAdminReqDto参数: {}", lostFoundAllByAdminReqDto.toString());
        if (lostFoundAllByAdminReqDto.isFetchAll()) {
            log.info("获取所有失物招领");
            // 如果 fetchAll 为 true，获取所有失物招领
            List<LostFoundTable> lostFounds = this.list();
            List<LostFoundByAdminDto> lostFoundDtos = BeanUtil.copyToList(lostFounds, LostFoundByAdminDto.class);
            log.info("lostFounds: {}", lostFounds);
            PageRespDto<LostFoundByAdminDto> pageRespDto = PageRespDto.of(lostFoundAllByAdminReqDto.getPageNum(), lostFoundAllByAdminReqDto.getPageSize(), lostFounds.size(), lostFoundDtos);
            return ResultPage.SUCCESS(pageRespDto);
        } else {
            // 如果 fetchAll 为 false，按分页查询
            Page<LostFoundTable> page = new Page<>(lostFoundAllByAdminReqDto.getPageNum(), lostFoundAllByAdminReqDto.getPageSize());
            Page<LostFoundTable> lostFoundPage = lostFoundTableMapper.selectPage(page, null);
            List<LostFoundTable> lostFounds = lostFoundPage.getRecords();
            List<LostFoundByAdminDto> lostFoundDtos = BeanUtil.copyToList(lostFounds, LostFoundByAdminDto.class);
            PageRespDto<LostFoundByAdminDto> pageRespDto = PageRespDto.of(lostFoundAllByAdminReqDto.getPageNum(), lostFoundAllByAdminReqDto.getPageSize(), lostFoundPage.getTotal(), lostFoundDtos);
            return ResultPage.SUCCESS(pageRespDto);
        }
    }
}




