package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.dto.LostFoundDto;
import com.ewing.domain.entity.LostFoundTable;

import java.util.List;

/**
* @author ewing
* @description 针对表【lost_found_table(失物遗失招领表)】的数据库操作Mapper
* @createDate 2024-10-14 16:46:53
* @Entity generator.domain.LostFoundTable
*/
public interface LostFoundTableMapper extends BaseMapper<LostFoundTable> {

    List<LostFoundTableMapper> getAllLostFounds();
}




