package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.entity.RoleTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author ewing
* @description 针对表【role_table(角色表)】的数据库操作Mapper
* @createDate 2024-10-13 19:43:56
* @Entity generator.domain.RoleTable
*/
public interface RoleTableMapper extends BaseMapper<RoleTable> {
    List<RoleTable> getRolesByUserId(@Param("userId") String userId);

    List<RoleTable> selectAllRoles();
}




