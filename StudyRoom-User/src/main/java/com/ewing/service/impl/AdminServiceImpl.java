package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.dto.*;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.resp.LostFoundByAdminDto;
import com.ewing.domain.entity.*;
import com.ewing.feign.RoomClient;
import com.ewing.manager.RedisCache;
import com.ewing.mapper.PermissionTableMapper;
import com.ewing.mapper.RolePermissionsMapper;
import com.ewing.mapper.RoleTableMapper;
import com.ewing.mapper.UserTableMapper;
import com.ewing.service.*;
import constant.CacheConstant;
import constant.DataBaseConstant;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import context.UserContext;
import context.UserInfoContextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Ewing
 * @Date: 2024-10-28-13:39
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<UserTableMapper, UserTable> implements AdminService {
    private final RedisCache redisCache;
    private final UserTableMapper userTableMapper;

    private final PermissionTableMapper permissionTableMapper;
    private final RoleTableMapper roleTableMapper;

    private final RoomClient roomClient;

    private final PermissionTableService permissionTableService;

    private final RoleTableService roleTableService;

    private final MessageTableService messageTableService;
    private final RolePermissionsMapper rolePermissionsMapper;

    @Override
    public ResultPage<Void> disable(UserDisableReqDto userDisableReqDto) {
//        UserTable user = getById(userDisableReqDto.getUserId());
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userDisableReqDto.getUserId())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
        UserTable user = userTableMapper.selectOne(queryWrapper);
        //将状态设置为封禁
        log.info("用户封禁，用户ID:{}", userDisableReqDto.getUserId());
        log.info("用户封禁信息：{}", user.toString());
        user.setStatus(SystemConfigConstant.USER_ACCOUNT_BANNED);
        userTableMapper.update(user, queryWrapper);

        //跟新缓存
        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userDisableReqDto.getUserId(), BeanUtil.copyProperties(user, UserDto.class));
        return ResultPage.SUCCESS(ErrorEnum.USER_ACCOUNT_BANNED);
    }


    @Override
    public ResultPage<Void> unblock(UserDisableReqDto userDisableReqDto) {
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userDisableReqDto.getUserId())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
        UserTable user = userTableMapper.selectOne(queryWrapper);
        //将状态设置为封禁
        log.info("用户解封，用户ID:{}", userDisableReqDto.getUserId());
        log.info("用户解封信息：{}", user.toString());
        user.setStatus(SystemConfigConstant.USER_ACCOUNT_UNLOCK);
        userTableMapper.update(user, queryWrapper);

        //跟新缓存
        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userDisableReqDto.getUserId(), BeanUtil.copyProperties(user, UserDto.class));
        return ResultPage.SUCCESS(ErrorEnum.USER_ACCOUNT_UNLOCK);
    }


    @Override
    public ResultPage<Void> delete(UserDeleteReqDto userDeleteReqDto) {
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userDeleteReqDto.getUserId())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());

        //逻辑删除 delFlag标志为1
        UserTable user = userTableMapper.selectOne(queryWrapper);
        user.setDelFlag(SystemConfigConstant.USER_ACCOUNT_DELFLAG);
        //更新到数据库
        userTableMapper.update(user, queryWrapper);
        //删除缓存
        redisCache.deleteObject(CacheConstant.USERS_CACHE_KEY + userDeleteReqDto.getUserId());
        //返回删除成功
        return ResultPage.SUCCESS(ErrorEnum.USER_ACCOUNT_DELFLAG);
    }

    @Override
    public ResultPage<Void> undelete(UserDeleteReqDto userDeleteReqDto) {
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userDeleteReqDto.getUserId())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());

        //逻辑删除 delFlag标志为1
        UserTable user = userTableMapper.selectOne(queryWrapper);
        user.setDelFlag(SystemConfigConstant.USER_ACCOUNT_UNDELFLAG);
        //更新到数据库
        userTableMapper.update(user, queryWrapper);

        //返回恢复删除成功
        return ResultPage.SUCCESS(ErrorEnum.USER_ACCOUNT_UNDELFLAG);
    }


    @Override
    public ResultPage<Void> someMethodToCreateStudyRoom(RoomCreateReqDto roomDTO) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
//        log.info("当前用户id：{}", userContext.getUserId());
//        log.info("当前用户角色：{}", userContext.getPermissions());
//        log.info("当前用户名：{}", userContext.getUserName());

        //判断userContext时候为空以及判断是否有MANAGE_STUDY_ROOM权限
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("MANAGE_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        // 调用自习室管理微服务的创建接口
        JSONObject jsonObject = JSONObject.parseObject(roomClient.createStudyRoom(roomDTO));
        Object code = jsonObject.get("code");
//        log.info("自习室创建结果code：{}", code);
        return switch (code.toString()) {
            case "A4016" -> ResultPage.SUCCESS(ErrorEnum.ROOM_CREATE_SUCCESS);
            case "A4017" -> ResultPage.FAIL(ErrorEnum.ROOM_CREATE_FAILURE);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.FAIL(ErrorEnum.UKNOWN_ERROR);
        };
    }


    @Override
    public ResultPage<Void> someMethodToUpdateStudyRoom(RoomUpdateReqDto roomDTO) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        //判断userContext时候为空以及判断是否有MANAGE_STUDY_ROOM权限
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("MANAGE_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        JSONObject jsonObject = JSONObject.parseObject(roomClient.updateStudyRoom(roomDTO));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4018" -> ResultPage.SUCCESS(ErrorEnum.ROOM_UPDATE_SUCCESS);
            case "A4019" -> ResultPage.FAIL(ErrorEnum.ROOM_UPDATE_FAILURE);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.FAIL(ErrorEnum.UKNOWN_ERROR);
        };

    }

    @Override
    public ResultPage<Void> someMethodToDeleteStudyRoom(String roomId) {
        return null;
    }

    @Override
    public ResultPage<List<UserTable>> getAllUsers() {
        return null;
    }

    @Override
    public ResultPage<List<String>> getAllPermissions() {
        return null;
    }

    @Override
    public ResultPage<Void> addPermission(String permission) {
        return null;
    }

    @Override
    public ResultPage<Void> deletePermission(String permission) {
        return null;
    }

    @Override
    public ResultPage<Void> assignPermission(UserTable userTable) {
        return null;
    }

    @Override
    public ResultPage<Object> someMethodToQueryStudyRoom(String roomId) {
        return null;
    }

    @Override
    public ResultPage<Object> someMethodToEditStudyRoom(String roomId, String seatId, String userId, Object object) {
        return null;
    }

    @Override
    public ResultPage<Object> someMethodToEditSeat(String roomId, String seatId, Object object) {
        return null;
    }

    @Override
    public ResultPage<PageRespDto<UserInfoByAdminDto>> managerAllUsers(UserInfoAllByAdminReqDto userInfoAllByAdminReqDto) {
        log.info("userInfoAllByAdminReqDto: {}", userInfoAllByAdminReqDto.toString());
        if (userInfoAllByAdminReqDto.isFetchAll()) {
            log.info("获取所有失物招领");
            // 如果 fetchAll 为 true，获取所有失物招领
            List<UserTable> users = this.list();
            List<UserInfoByAdminDto> userInfoByAdminDtos = BeanUtil.copyToList(users, UserInfoByAdminDto.class);
            userInfoByAdminDtos.forEach(user -> {
                Set<String> roles = roleTableService.getRolesByUserId(user.getUserId());
                Set<String> permissions = permissionTableService.getPermissionsByUserId(user.getUserId());
                user.setRoles(roles);
                user.setPermissions(permissions);
            });
            PageRespDto<UserInfoByAdminDto> pageRespDto = PageRespDto.of(userInfoAllByAdminReqDto.getPageNum(), userInfoAllByAdminReqDto.getPageSize(), users.size(), userInfoByAdminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        } else {
            // 如果 fetchAll 为 false，按分页查询
            Page<UserTable> page = new Page<>(userInfoAllByAdminReqDto.getPageNum(), userInfoAllByAdminReqDto.getPageSize());
            Page<UserTable> userPage = userTableMapper.selectPage(page, null);
            List<UserTable> users = userPage.getRecords();
            List<UserInfoByAdminDto> userInfoByAdminDtos = BeanUtil.copyToList(users, UserInfoByAdminDto.class);
            userInfoByAdminDtos.forEach(user -> {
                Set<String> roles = roleTableService.getRolesByUserId(user.getUserId());
                Set<String> permissions = permissionTableService.getPermissionsByUserId(user.getUserId());
                user.setRoles(roles);
                user.setPermissions(permissions);
            });
            PageRespDto<UserInfoByAdminDto> pageRespDto = PageRespDto.of(userInfoAllByAdminReqDto.getPageNum(), userInfoAllByAdminReqDto.getPageSize(), userPage.getTotal(), userInfoByAdminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        }
    }

    @Override
    @Transactional
    public ResultPage<Void> UpdateUserByAdmin(UserUpdateByAdminReqDto userUpdateByAdminReqDto) {
        String userId = userUpdateByAdminReqDto.getUserId();
        Integer creditScore = userUpdateByAdminReqDto.getCreditScore();
        Set<String> roles = userUpdateByAdminReqDto.getRoles();
//        Set<String> permissions = userUpdateByAdminReqDto.getPermissions();

        // 更新用户的信用分数
        if (creditScore != null) {
            userTableMapper.updateCreditScoreByUserId(userId, creditScore);
        }

        // 更新用户的角色
        if (roles != null) {
            // 删除旧的角色
            userTableMapper.deleteUserRolesByUserId(userId);
            // 插入新的角色
            for (String role : roles) {
                userTableMapper.insertUserRole(userId, role);
            }
        }
//
//        // 更新用户的权限
//        if (permissions != null) {
//            // 删除旧的权限
//            userTableMapper.deleteUserPermissionsByUserId(userId);
//            // 插入新的权限
//            for (String permission : permissions) {
//                userTableMapper.insertUserPermission(userId, permission);
//            }
//        }

        return ResultPage.SUCCESS();
    }

    @Override
    public ResultPage<PageRespDto<RolesPermissionByAdminDto>> getAllRolePermissions(RolesPermissionsAllByAdminReqDto rolesPermissionsAllByAdminReqDto) {
        return roleTableService.getAllRolePermissions(rolesPermissionsAllByAdminReqDto);
    }

    @Override
    @Transactional
    public ResultPage<Void> createRolesByAdmin(RolesCreateByAdminReqDto rolesCreateByAdminReqDto) {
        String roleName = rolesCreateByAdminReqDto.getRoleName();
        String roleDesc = rolesCreateByAdminReqDto.getRoleDesc();
        String roleId = rolesCreateByAdminReqDto.getRoleId();

        log.info("roleName: {}, roleDesc: {}, roleId: {}", roleName, roleDesc, roleId);
        // 创建角色
        RoleTable role = new RoleTable();
        role.setRoleName(roleName);
        role.setDescription(roleDesc);
        role.setRoleId(roleId);
        int insert = roleTableMapper.insert(role);

        return insert > 0 ? ResultPage.SUCCESS(ErrorEnum.ROLE_CREATE_SUCCESS) : ResultPage.FAIL(ErrorEnum.ROLE_CREATE_FAILURE);
    }


    @Override
    @Transactional
    public ResultPage<Void> createPermissionByAdmin(PermissionsCreateByAdminReqDto permissionsCreateByAdminReqDto) {
        String permissionName = permissionsCreateByAdminReqDto.getPermissionName();
        String permissionDesc = permissionsCreateByAdminReqDto.getPermissionDesc();
        String permissionId = permissionsCreateByAdminReqDto.getPermissionId();

        // 创建权限
        PermissionTable permission = new PermissionTable();
        permission.setPermissionName(permissionName);
        permission.setDescription(permissionDesc);
        permission.setPermissionId(permissionId);
        int insert = permissionTableMapper.insert(permission);

        return insert > 0? ResultPage.SUCCESS(ErrorEnum.PERMISSION_CREATE_SUCCESS) : ResultPage.FAIL(ErrorEnum.PERMISSION_CREATE_FAILURE);
    }

    @Override
    public ResultPage<Void> updateRolePermissionsByAdmin(UpdateRolesByAdminReqDto updateRolesByAdminReqDto) {
        String roleId = updateRolesByAdminReqDto.getRoleId();
        Set<String> permissions = updateRolesByAdminReqDto.getPermissions();

        // 检查角色是否存在
        QueryWrapper<RoleTable> roleQuery = new QueryWrapper<>();
        roleQuery.eq("role_id", roleId);
        RoleTable role = roleTableMapper.selectOne(roleQuery);
        if (ObjectUtil.isNull(role)) {
            return ResultPage.FAIL(ErrorEnum.ROLE_NOT_EXIT);
        }


        // 检查权限是否存在
        QueryWrapper<PermissionTable> permissionQuery = new QueryWrapper<>();
        permissionQuery.in("permission_id", permissions);
        List<PermissionTable> existingPermissions = permissionTableMapper.selectList(permissionQuery);
        Set<String> existingPermissionIds = existingPermissions.stream()
                .map(PermissionTable::getPermissionId)
                .collect(Collectors.toSet());

        // 检查是否有不存在的权限
        Set<String> nonExistingPermissions = permissions.stream()
                .filter(permissionId -> !existingPermissionIds.contains(permissionId))
                .collect(Collectors.toSet());

        if (!nonExistingPermissions.isEmpty()) {
            log.error("以下权限不存在: {}", nonExistingPermissions);
            return ResultPage.FAIL(ErrorEnum.PERMISSION_NOT_EXIST);
        }


        // 删除旧的权限
        QueryWrapper<RolePermissions> deleteQuery = new QueryWrapper<>();
        deleteQuery.eq("role_id", roleId);
        rolePermissionsMapper.delete(deleteQuery);

        // 插入新的权限
        List<RolePermissions> newPermissions = permissions.stream()
                .map(permissionId -> new RolePermissions(null, roleId, permissionId))
                .collect(Collectors.toList());
        rolePermissionsMapper.insert(newPermissions);

        return ResultPage.SUCCESS(ErrorEnum.ROLE_PERMISSIONS_UPDATE_SUCCESS);
    }

    @Override
    public ResultPage<List<RolesAllByAdminDto>> getAllRolesByAdmin() {
        List<RoleTable> roleTables = roleTableMapper.selectList(null);
        List<RolesAllByAdminDto> rolesDtos = new ArrayList<>();
        if (!roleTables.isEmpty()) {
            Set<String> roleNames = roleTables.stream()
                    .map(RoleTable::getRoleName)
                    .collect(Collectors.toSet());
            RolesAllByAdminDto rolesDto = new RolesAllByAdminDto();
            rolesDto.setRoleName(roleNames);
            rolesDtos.add(rolesDto);
        }
        return ResultPage.SUCCESS(rolesDtos);
    }

    @Override
    public ResultPage<List<PermissionAllByAdminDto>> getAllPermissionsByAdmin() {
        List<PermissionTable> permissionTables = permissionTableMapper.selectList(null);
        List<PermissionAllByAdminDto> permissionsDtos = new ArrayList<>();
        if (!permissionTables.isEmpty()) {
            Set<String> permissionNames = permissionTables.stream()
                    .map(PermissionTable::getPermissionName)
                    .collect(Collectors.toSet());
            PermissionAllByAdminDto permissionsDto = new PermissionAllByAdminDto();
            permissionsDto.setPermissionName(permissionNames);
            permissionsDtos.add(permissionsDto);
        }
        return ResultPage.SUCCESS(permissionsDtos);
    }

    @Override
    public ResultPage<Object> getAllRooms(GetAllRoomByAdminReqDto getAllRoomByAdminReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        // 调用自习室管理微服务的获取所有房间接口
        JSONObject jsonObject = JSONObject.parseObject(roomClient.getAllRoomsByAdmin(getAllRoomByAdminReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4023" -> ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT_SEAT);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Object> someMethodToGetAllReservations(GetAllReservationByAdminReqDto getAllReservations) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        // 调用自习室管理微服务的获取所有预约信息接口
        JSONObject jsonObject = JSONObject.parseObject(roomClient.getAllReservations(getAllReservations));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4023" -> ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT_SEAT);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Void> someMethodToUpdateReservation(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        // 调用自习室管理微服务的获取所有预约信息接口
        JSONObject jsonObject = JSONObject.parseObject(roomClient.updateReservation(reservationUpdateByAdminReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4023" -> ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT_SEAT);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            case "A4048" -> ResultPage.SUCCESS(ErrorEnum.RESERVATION_UPDATE_SUCCESS);
            case "A4049" -> ResultPage.FAIL(ErrorEnum.RESERVATION_UPDATE_FAILURE);
            default -> ResultPage.FAIL(ErrorEnum.SYSTEM_ERROR);
        };
    }

    @Override
    public ResultPage<Void> deleteMessageByAdmin(String messageId) {
        return messageTableService.deleteMessageByAdmin(messageId);
    }

    @Override
    public ResultPage<PageRespDto<MessageByAdminDto>> getAllMessagesByAdmin(MessageByAdminReqDto messageByAdminReqDto) {
        return messageTableService.getAllMessagesByAdmin(messageByAdminReqDto);
    }

    @Override
    public ResultPage<Object> someMethodToGetAllReservationsByAdmin(GetAllReservationByAdminReqDto getAllReservationByAdminReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        // 调用自习室管理微服务的获取所有预约信息接口
        JSONObject jsonObject = JSONObject.parseObject(roomClient.getAllReservations(getAllReservationByAdminReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Void> someMethodToUpdateReservationByAdmin(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        // 调用自习室管理微服务的获取所有预约信息接口
        JSONObject jsonObject = JSONObject.parseObject(roomClient.updateReservation(reservationUpdateByAdminReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            case "A4048" -> ResultPage.SUCCESS(ErrorEnum.RESERVATION_UPDATE_SUCCESS);
            case "A4049" -> ResultPage.FAIL(ErrorEnum.RESERVATION_UPDATE_FAILURE);
            default -> ResultPage.FAIL(ErrorEnum.SYSTEM_ERROR);
        };
    }

}
