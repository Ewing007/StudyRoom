package com.ewing.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.entity.PermissionTable;

import com.ewing.mapper.PermissionTableMapper;
import com.ewing.service.PermissionTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author ewing
* @description 针对表【permission_table(权限表)】的数据库操作Service实现
* @createDate 2024-10-13 19:44:14
*/
@Service
@RequiredArgsConstructor
public class PermissionTableServiceImpl extends ServiceImpl<PermissionTableMapper, PermissionTable>
    implements IService<PermissionTable>, PermissionTableService {

    private final PermissionTableMapper permissionMapper;
    @Override
    public Set<String> getPermissionsByUserId(String userId) {
        List<PermissionTable> permissions = permissionMapper.getPermissionsByUserId(userId);
        Set<String> permissionNames = new HashSet<>();
        for (PermissionTable permission : permissions) {
            permissionNames.add(permission.getPermissionName());
        }
        return permissionNames;
    }
}




