package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.dto.RolesPermissionByAdminDto;
import com.ewing.domain.dto.UserInfoByAdminDto;
import com.ewing.domain.dto.req.RolesPermissionsAllByAdminReqDto;
import com.ewing.domain.entity.PermissionTable;
import com.ewing.domain.entity.RoleTable;
import com.ewing.domain.entity.UserTable;
import com.ewing.mapper.PermissionTableMapper;
import com.ewing.mapper.RoleTableMapper;
import com.ewing.service.RoleTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author ewing
* @description 针对表【role_table(角色表)】的数据库操作Service实现
* @createDate 2024-10-13 19:43:56
*/
@Service
@RequiredArgsConstructor
public class RoleTableServiceImpl extends ServiceImpl<RoleTableMapper, RoleTable>
    implements RoleTableService {

    private final RoleTableMapper roleTableMapper;

    private final PermissionTableMapper permissionMapper;
    @Override
    public Set<String> getRolesByUserId(String userId) {
        List<RoleTable> roles = roleTableMapper.getRolesByUserId(userId);
        Set<String> roleNames = new HashSet<>();
        for (RoleTable role : roles) {
            roleNames.add(role.getRoleName());
        }
        return roleNames;
    }


    @Override
    public ResultPage<PageRespDto<RolesPermissionByAdminDto>> getAllRolePermissions(RolesPermissionsAllByAdminReqDto rolesPermissionsAllByAdminReqDto) {
        if (rolesPermissionsAllByAdminReqDto.isFetchAll()) {
            // 如果 fetchAll 为 true，获取所有角色和权限
            List<RoleTable> roleList = this.list();
            List<RolesPermissionByAdminDto> dtoList = roleList.stream().map(role -> {
                List<PermissionTable> permissionList = permissionMapper.selectPermissionsByRoleId(role.getRoleId());
                RolesPermissionByAdminDto dto = new RolesPermissionByAdminDto();
                dto.setRoleTable(role);
                dto.setPermissionTableList(permissionList);
                return dto;
            }).collect(Collectors.toList());
            PageRespDto<RolesPermissionByAdminDto> pageRespDto = PageRespDto.of(rolesPermissionsAllByAdminReqDto.getPageNum(), rolesPermissionsAllByAdminReqDto.getPageSize(), dtoList.size(), dtoList);

            return ResultPage.SUCCESS(pageRespDto);
        } else {
            // 如果 fetchAll 为 false，按分页查询
            Page<RoleTable> page = new Page<>(rolesPermissionsAllByAdminReqDto.getPageNum(), rolesPermissionsAllByAdminReqDto.getPageSize());
            Page<RoleTable> rolePage = roleTableMapper.selectPage(page, null);
            List<RoleTable> roleList = rolePage.getRecords();

            List<RolesPermissionByAdminDto> dtoList = roleList.stream().map(role -> {
                List<PermissionTable> permissionList = permissionMapper.selectPermissionsByRoleId(role.getRoleId());
                RolesPermissionByAdminDto dto = new RolesPermissionByAdminDto();
                dto.setRoleTable(role);
                dto.setPermissionTableList(permissionList);
                return dto;
            }).collect(Collectors.toList());

            PageRespDto<RolesPermissionByAdminDto> pageRespDto = PageRespDto.of(rolesPermissionsAllByAdminReqDto.getPageNum(), rolesPermissionsAllByAdminReqDto.getPageSize(), rolePage.getTotal(), dtoList);

            return ResultPage.SUCCESS(pageRespDto);
        }
    }
}




