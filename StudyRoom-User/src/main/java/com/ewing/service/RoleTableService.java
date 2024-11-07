package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.RolesPermissionByAdminDto;
import com.ewing.domain.dto.req.RolesPermissionsAllByAdminReqDto;
import com.ewing.domain.entity.RoleTable;

import java.util.Set;


/**
* @author ewing
* @description 针对表【role_table(角色表)】的数据库操作Service
* @createDate 2024-10-13 19:43:56
*/
public interface RoleTableService extends IService<RoleTable> {
    Set<String> getRolesByUserId(String userId);
    ResultPage<PageRespDto<RolesPermissionByAdminDto>> getAllRolePermissions(RolesPermissionsAllByAdminReqDto rolesPermissionsAllByAdminReqDto);
}
