package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.entity.PermissionTable;

import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @author ewing
* @description 针对表【permission_table(权限表)】的数据库操作Mapper
* @createDate 2024-10-13 19:54:15
* @Entity generator.domain.PermissionTable
*/
public interface PermissionTableMapper extends BaseMapper<PermissionTable> {

    List<String> getPermissionNamesByIds(@Param("permissionIds") List<String> permissionIds);

    List<PermissionTable> getPermissionsByUserId(@Param("userId") String userId);

    List<PermissionTable> selectPermissionsByRoleId(String roleId);
}




