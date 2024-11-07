package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.req.LogReqDto;
import com.ewing.domain.dto.resp.LogRespDto;
import com.ewing.domain.entity.LogTable;

import java.util.List;


/**
* @author ewing
* @description 针对表【log_table(日志表)】的数据库操作Service
* @createDate 2024-10-11 11:41:02
*/
public interface LogTableService extends IService<LogTable> {

    ResultPage<List<LogRespDto>> recording();

    ResultPage<PageRespDto<LogRespDto>> getAllLogs(LogReqDto logReqDto);
}
