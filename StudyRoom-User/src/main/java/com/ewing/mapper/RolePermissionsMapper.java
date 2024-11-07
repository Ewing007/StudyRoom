package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.entity.RolePermissions;

import java.util.List;


/**
* @author ewing
* @description 针对表【role_permissions(角色权限关联表)】的数据库操作Mapper
* @createDate 2024-10-13 19:44:06
* @Entity generator.domain.RolePermissions
*/
public interface RolePermissionsMapper extends BaseMapper<RolePermissions> {

    List<String> getPermissionsByRoleIds(List<String> roleIds);
}




