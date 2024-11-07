package com.ewing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.entity.PermissionTable;

import java.util.Set;

/**
* @author ewing
* @description 针对表【permission_table(权限表)】的数据库操作Service
* @createDate 2024-10-13 19:44:14
*/
public interface PermissionTableService extends IService<PermissionTable> {
    Set<String> getPermissionsByUserId(String userId);

}
