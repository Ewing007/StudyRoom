package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.LostFoundDto;
import com.ewing.domain.dto.resp.LostFoundByAdminDto;
import com.ewing.domain.entity.LostFoundTable;

import Exception.BusinessException;

import java.util.List;
/**
* @author ewing
* @description 针对表【lost_found_table(失物遗失招领表)】的数据库操作Service
* @createDate 2024-10-14 16:46:53
*/
public interface LostFoundTableService extends IService<LostFoundTable> {

    ResultPage<Void> post(LostFoundReqDto lostFoundReqDto);

    ResultPage<PageRespDto<LostFoundDto>> viewPersona(LostFoundByUserReqDto lostFoundByUserId);

    ResultPage<List<LostFoundDto>> all();

    ResultPage<LostFoundDto> getAllByLostId(Long lostId) throws BusinessException;

    ResultPage<Void> managerByAdmin(LostFoundAdminReqDto lostFoundAdminReqDto);

    ResultPage<LostFoundDto> persona(LostFoundPersonaReqDto lostFoundPersonaReqDto);

    ResultPage<PageRespDto<LostFoundByAdminDto>> getAllLoststByAdmin(LostFoundAllByAdminReqDto lostFoundAllByAdminReqDto);
}
