package com.ewing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.entity.RolePermissions;
import com.ewing.mapper.RolePermissionsMapper;
import com.ewing.service.RolePermissionsService;
import org.springframework.stereotype.Service;

/**
* @author ewing
* @description 针对表【role_permissions(角色权限关联表)】的数据库操作Service实现
* @createDate 2024-10-13 19:44:06
*/
@Service
public class RolePermissionsServiceImpl extends ServiceImpl<RolePermissionsMapper, RolePermissions>
    implements RolePermissionsService {

}




