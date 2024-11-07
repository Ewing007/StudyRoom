package com.ewing.service.impl;

import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.dto.resp.LogRespDto;
import com.ewing.domain.entity.LogTable;
import com.ewing.mapper.LogTableMapper;
import com.ewing.service.LogTableService;
import constant.DataBaseConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ewing
* @description 针对表【log_table(日志表)】的数据库操作Service实现
* @createDate 2024-10-11 11:41:02
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class LogTableServiceImpl extends ServiceImpl<LogTableMapper, LogTable>
    implements LogTableService {

    private final LogTableMapper logTableMapper;
    @Override
    public ResultPage<List<LogRespDto>> recording() {
        log.info("查寻日志");
        QueryWrapper<LogTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.last(DataBaseConstant.sqlEnum.LIMIT_5.getSql());
        List<LogTable> logTable = logTableMapper.selectList(queryWrapper);
        List<LogRespDto> logRespDtos = BeanUtil.copyToList(logTable, LogRespDto.class);
        log.info("{}",logTable);
        return ResultPage.SUCCESS(logRespDtos);
    }
}




