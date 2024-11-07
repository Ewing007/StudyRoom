package com.ewing.service.impl;

import Result.ResultPage;
import Utils.JwtUtils;
import cn.hutool.core.date.DateTime;


import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.resp.UserUpdateRespDto;
import com.ewing.domain.entity.PermissionTable;
import com.ewing.domain.entity.RoleTable;
import com.ewing.domain.entity.UserRoles;
import com.ewing.feign.RoomClient;
import com.ewing.manager.RedisCache;
import Utils.SnowUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.dto.UserDto;
import com.ewing.domain.dto.resp.UserRegisterRespDto;
import com.ewing.domain.dto.resp.UserLoginRespDto;
import com.ewing.domain.entity.UserTable;
import com.ewing.manager.UserVerityCodeManager;
import com.ewing.mapper.*;
import com.ewing.service.UserTableService;
import constant.CacheConstant;
import constant.DataBaseConstant;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import context.UserContext;
import context.UserInfoContextHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import Exception.BusinessException;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


/**
* @author ewing
* @description 针对表【user_table(用户表)】的数据库操作Service实现
* @createDate 2024-10-09 21:12:24
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable>
    implements UserTableService {

    private final UserTableMapper userTableMapper;

    private final UserRolesMapper userRolesMapper;

    private final PermissionTableMapper permissionTableMapper;

    private final RolePermissionsMapper rolePermissionsMapper;

    private final RoleTableMapper roleTableMapper;
    private final UserVerityCodeManager userVerityCodeManager;
    private final RedisCache redisCache;

    private final RoomClient roomClient;

    @Override
    public ResultPage<UserLoginRespDto> login(UserLoginReqDto userLoginReqDto) throws BusinessException {
        //校验验证码是否正确
        if(!userVerityCodeManager.imgVerityCodeOk(userLoginReqDto.getSessionId(), userLoginReqDto.getImgVerityCode())
        ) {
            throw new BusinessException(ErrorEnum.USER_VERITY_CODE_ERROR);
        }
        //校验用户信息是否存在
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERNAME_PHONE, userLoginReqDto.getUsername())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
        UserTable user = userTableMapper.selectOne(queryWrapper);

        //用户不存在，返回 USER_NOT_EXIT
        if(ObjectUtil.isEmpty(user) || SystemConfigConstant.USER_ACCOUNT_DELFLAG.equals(user.getDelFlag())) {
            throw new BusinessException(ErrorEnum.USER_NOT_EXIT);
        }
        //用户存在，判断密码是否正确
        //用户密码错误，返回错误提示
        if(!ObjectUtil.equal(user.getPassword(), DigestUtils.md5DigestAsHex(userLoginReqDto.getPassword().getBytes(StandardCharsets.UTF_8)))) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_ERROR);
        }


        //用户密码正确，将用户信息分装成UserDto对象
        UserDto userDto = setUserPerssions(user);
//        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);

        //Jwt 构建 token
        String token = JwtUtils.generatorToken(user.getUserId(), SystemConfigConstant.STUDY_ROOM_FRONT_KEY);

        //将token缓存进redis
        redisCache.setCacheObject(CacheConstant.TOKEN_VERITY_CACHE_KEY + userDto.getUserId(),token);
        //将userDto 缓存redis
        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userDto.getUserId(),userDto);

        //将token 与 UserDto 封装成 UserRespDto对象
        UserLoginRespDto userLoginRespDto = new UserLoginRespDto(userDto, token);

        //返回ResultPage信息
        return ResultPage.SUCCESS(
                userLoginRespDto
        );
    }

    @Override
    public ResultPage<UserRegisterRespDto> register(UserRegisterReqDto userRegisterReqDto) throws BusinessException {

        //图形验证码校验
        if(!userVerityCodeManager.imgVerityCodeOk(userRegisterReqDto.getSessionId(), userRegisterReqDto.getImgVerityCode())
        ) {
            throw new BusinessException(ErrorEnum.USER_VERITY_CODE_ERROR);
        }
        //校验用户手机是否存在
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERNAME_PHONE, userRegisterReqDto.getPhone())
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
        //手机号存在 throw USER_PHONE_EXIT
        if(userTableMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException(ErrorEnum.USER_PHONE_EXIT);
        }

        //注册用户信息
        UserTable user = new UserTable();
        user.setUserId(SnowUtils.getSnowflakeNextIdStr());
        user.setUserName(userRegisterReqDto.getUsername());
        user.setPhoneNumber(userRegisterReqDto.getPhone());
        user.setCreateTime(new DateTime());
        user.setUpdateTime(new DateTime());
        user.setPassword(
                DigestUtils.md5DigestAsHex(userRegisterReqDto.getPassword().getBytes(StandardCharsets.UTF_8)));

        //插入数据库
        userTableMapper.insert(user);

        //返回部分用户信息
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);

        return ResultPage.SUCCESS(
                UserRegisterRespDto.builder()
                        .userDto(userDto)
                        .token(JwtUtils.generatorToken(userDto.getUserId(), SystemConfigConstant.STUDY_ROOM_FRONT_KEY))
                        .build()
        );
    }

    @Override
    public ResultPage<Void> logout() {
        //从本地线程变量获取用户id
        String userId = UserInfoContextHandler.getUserContext().getUserId();
        String userName = UserInfoContextHandler.getUserContext().getUserName();
        log.info("用户退出，用户ID:{}", userId);
        log.info("用户退出，用户:{}", userName);
        //清除redis缓存
        boolean a = redisCache.deleteObject(CacheConstant.TOKEN_VERITY_CACHE_KEY + userId);
        boolean b = redisCache.deleteObject(CacheConstant.USERS_CACHE_KEY + userId);
        log.info("用户退出，用户ID:{}，删除用户缓存结果:{}", userId, b);
        log.info("用户退出，用户ID:{}，删除token缓存结果:{}", userId, a);

        // 清理本地线程变量
        UserInfoContextHandler.clear();
        //返回
        return ResultPage.SUCCESS();
    }

//    @Override
//    public ResultPage<UserUpdateRespDto> update(UserUpdateReqDto userUpdateReqDto) {
//        //从本地线程变量获取用户id
//        String userId = UserInfoContextHandler.getUserContext().getUserId();
//
//        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userId)
//                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
//
//        UserDto userCache = (UserDto) redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);
//
//        UserTable user = new UserTable();
//        user.setUserId(userId);
//        user.setUserName(userUpdateReqDto.getUserName());
//        user.setSex(userUpdateReqDto.getSex());
//        user.setAvatar(userUpdateReqDto.getAvatar());
//        user.setPhoneNumber(userUpdateReqDto.getPhoneNumber());
//        user.setEmail(userUpdateReqDto.getEmail());
//        user.setUpdateTime(new DateTime());
//        user.setCreditScore(userCache.getCreditScore());
//
//
//        log.info("用户信息更新，用户信息:{}", user);
//        //更新用户信息
//        userTableMapper.update(user, queryWrapper);
//
//        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
//        //更新到redis缓存
//        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userId, userDto);
//        return ResultPage.SUCCESS(
//                UserUpdateRespDto.builder()
//                        .userDto(userDto)
//                        .message("更新成功")
//                        .build()
//        );
//
//    }

    @Override
    public ResultPage<UserUpdateRespDto> update(UserUpdateReqDto userUpdateReqDto) throws BusinessException {
        // 从本地线程变量获取用户id
        String userId = UserInfoContextHandler.getUserContext().getUserId();

        // 查询当前用户的缓存信息
        UserDto userCache = (UserDto) redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);

        log.info("用户信息更新，缓存中用户信息为:{}", userCache.toString());
        // 检查邮箱和手机号是否已存在
        QueryWrapper<UserTable> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USER_EMAIL, userUpdateReqDto.getEmail())
                .ne(DataBaseConstant.UserInfoTable.COLUMN_USERID, userId);

        QueryWrapper<UserTable> phoneQueryWrapper = new QueryWrapper<>();
        phoneQueryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERNAME_PHONE, userUpdateReqDto.getPhoneNumber())
                .ne(DataBaseConstant.UserInfoTable.COLUMN_USERID, userId);

        if (userTableMapper.exists(emailQueryWrapper)) {
            throw new BusinessException(ErrorEnum.USER_EMAIL_EXIT); //邮箱已存在
        }

        if (userTableMapper.exists(phoneQueryWrapper)) {
            throw new BusinessException(ErrorEnum.USER_PHONE_EXIT); //手机号已存在
        }

        // 构建更新对象
        UserTable user = new UserTable();
        user.setUserId(userId);
        user.setUserName(userUpdateReqDto.getUserName());
        user.setSex(userUpdateReqDto.getSex());
        user.setAvatar(userUpdateReqDto.getAvatar());
        user.setPhoneNumber(userUpdateReqDto.getPhoneNumber());
        user.setEmail(userUpdateReqDto.getEmail());
        user.setUpdateTime(new DateTime());
        user.setCreditScore(userCache.getCreditScore());

        log.info("用户信息更新，用户信息:{}", user);

        // 更新用户信息
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userId)
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());
        userTableMapper.update(user, queryWrapper);

        // 更新到Redis缓存
        UserDto userPerssions = setUserPerssions(user);
        UserDto userDto = BeanUtil.copyProperties(userPerssions, UserDto.class);
        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userId, userDto);

        return ResultPage.SUCCESS(
                UserUpdateRespDto.builder()
                        .userDto(userDto)
                        .message("更新成功")
                        .build()
        );
    }

    @Override
    public UserDto getUserDtoById(String userId) throws BusinessException {


        //从redsi缓存获取
        UserDto user = redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);
        if (!ObjectUtil.isNull(user)) {
            return user;
        }

        UserTable userTable = this.getById(userId);

        //用户不存在返回null
        if(ObjectUtil.isNull(userTable)) {
            return null;
        }

        UserDto userDto = setUserPerssions(userTable);

        // 缓存UserDto
        redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userId, userDto);

        return userDto;
    }

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

    /**
     * 设置用户权限信息
     * @param userTable
     * @return
     */
    private UserDto setUserPerssions(UserTable userTable) {

        UserDto userDto = BeanUtil.copyProperties(userTable, UserDto.class);

        //获取用户角色信息
        List<UserRoles> userRolesList = userRolesMapper.selectList(
                new QueryWrapper<UserRoles>().eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userTable.getUserId())
        );

        List<String> roleIds = userRolesList.stream()
                .map(UserRoles::getRoleId)
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            userDto.setPermissions(Collections.emptySet());
            // 缓存UserDto
            redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY, userDto);
            return userDto;
        }


        // 获取所有角色对应的权限
        List<String> permissionIds = rolePermissionsMapper.getPermissionsByRoleIds(roleIds);
        if (permissionIds.isEmpty()) {
            userDto.setPermissions(Collections.emptySet());
            userDto.setRoles(Collections.emptySet());
            // 缓存UserDto
            redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY, userDto);
            return userDto;
        }

        // 获取权限名称
        Set<String> permissions = new HashSet<>();
        List<PermissionTable> permissionList = permissionTableMapper.selectBatchIds(permissionIds);
        permissions = permissionList.stream()
                .map(PermissionTable::getPermissionName)
                .collect(Collectors.toSet());

        // 添加权限ID即为权限名称
        permissions.addAll(permissionIds);
        userDto.setPermissions(permissions);

        // 获取角色名称
        Set<String> roleNames = new HashSet<>();
        List<RoleTable> roleList = roleTableMapper.selectBatchIds(roleIds);
        roleNames = roleList.stream()
                .map(RoleTable::getRoleName)
                .collect(Collectors.toSet());

        // 设置角色名称
        userDto.setRoles(roleNames);
        //设置为已登录状态
        userDto.setIsLogin(true);
        return userDto;

    }

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    public UserTable getById(String userId) {
        //检验用户是否存在
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userId)
                .last(DataBaseConstant.sqlEnum.LIMIT_1.getSql());

        //查询数据库
        UserTable user = userTableMapper.selectOne(queryWrapper);

        //返回用户信息
        return user;
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
    public ResultPage<Void> someMethodToUpdateStudyRoom(String roomId, RoomUpdateReqDto roomDTO) {
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
//        String s = roomClient.updateStudyRoom(roomId, roomDTO);
//        log.info("自习室更新结果：{}", s);
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
    public ResultPage<String> someMethodToBookStudyRoom(BookRoomReqDto bookRoomReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        log.info("自习室预定请求参数：{}", bookRoomReqDto);
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
//        String s = roomClient.bookStudyRoom(bookRoomReqDto);
//        log.info("自习室预定结果：{}", s);
        JSONObject jsonObject = JSONObject.parseObject(roomClient.bookStudyRoom(bookRoomReqDto));
        Object code = jsonObject.get("code");
        log.info("自习室预定结果：{}", jsonObject);
        log.info("预定结果code：{}", code);
        return switch (code.toString()) {
            case "A4014" -> ResultPage.SUCCESS(ErrorEnum.RESERVATION_CREATE_SUCCESS);
            case "A4025" -> ResultPage.FAIL(ErrorEnum.SEAT_NOT_FOUND);
            case "A4026" -> ResultPage.FAIL(ErrorEnum.TIME_SLOT_NOT_FOUND);
            case "A4027" -> ResultPage.FAIL(ErrorEnum.RESERVATION_TIME_CONFLICT);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.FAIL(ErrorEnum.UKNOWN_ERROR);
        };
    }

    @Override
    public ResultPage<String> someMethodToCancelStudyRoom(String reservationId) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        log.info("自习室预定ID：{}", reservationId);
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        String s = roomClient.cancelStudyRoom(reservationId);
        JSONObject jsonObject = JSONObject.parseObject(roomClient.cancelStudyRoom(reservationId));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4032" -> ResultPage.SUCCESS(ErrorEnum.RESERVATION_CANCEL_SUCCESS);
            case "A4029" -> ResultPage.FAIL(ErrorEnum.RESERVATION_NOT_FOUND);
            case "A4030" -> ResultPage.FAIL(ErrorEnum.RESERVATION_ALREADY_CANCELLED);
            case "A4031" -> ResultPage.FAIL(ErrorEnum.RESERVATION_CANCEL_FAILURE);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            case "A4050" -> ResultPage.FAIL(ErrorEnum.RESERVATION_CANCEL_TOO_LATE);
            default -> ResultPage.FAIL(ErrorEnum.UKNOWN_ERROR);
        };


    }

    @Override
    public ResultPage<Object> someMethodToQueryReservation(ReservationByUserReqDto reservationByUserReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        JSONObject jsonObject = JSONObject.parseObject(roomClient.getReservations(reservationByUserReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4028" -> ResultPage.FAIL(ErrorEnum.NO_RESERVATIONS_FOUND);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };

    }

    @Override
    public ResultPage<Object> someMethodToQuerySeats(String roomId) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        JSONObject jsonObject = JSONObject.parseObject(roomClient.getSeatsByRoomId(roomId));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4023" -> ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT_SEAT);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Object> someMethodToGetAllStudyRooms() {
        String rooms = roomClient.getAllStudyRooms();
        log.info("自习室座位查询结果：{}", rooms);
        JSONObject jsonObject = JSONObject.parseObject(roomClient.getAllStudyRooms());
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4023" -> ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT_SEAT);
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Void> updateUserAvatar(String avatarUrl) throws BusinessException {
        UserContext userContext = UserInfoContextHandler.getUserContext();
        if (ObjectUtil.isNull(userContext)) {
            throw new BusinessException(ErrorEnum.USERNOT_LOGIN);
        }

        UserTable user = new UserTable();
        user.setAvatar(avatarUrl);
        int update = userTableMapper.update(user, new QueryWrapper<UserTable>().eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userContext.getUserId()));

        if (update == 1) {
            // 从 Redis 中删除旧的缓存数据
            redisCache.deleteObject(CacheConstant.USERS_CACHE_KEY + userContext.getUserId());

            // 将新的数据重新写入 Redis
            UserDto userDto = getUserDtoById(userContext.getUserId());
            redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userContext.getUserId(), userDto);
            return ResultPage.SUCCESS(ErrorEnum.USER_AVATAR_UPDATE_SUCCESS);
        } else {
            return ResultPage.FAIL(ErrorEnum.USER_AVATAR_UPDATE_FAILURE);
        }    }

    @Override
    public ResultPage<Object> someMethodToQueryStudyRoomByCondition(RoomQueryByConditionReqDto queryConditionReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        log.info("当前用户id：{}", userContext.getUserId());
        log.info("当前用户角色：{}", userContext.getPermissions());
        log.info("当前用户名：{}", userContext.getUserName());
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        JSONObject jsonObject = JSONObject.parseObject(roomClient.searchStudyRooms(queryConditionReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Object> someMethodToGetAllTime() {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        JSONObject jsonObject = JSONObject.parseObject(roomClient.getAllTime());
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };

    }

    @Override
    public ResultPage<Object> someMethodToGetTimeSlotsByRoomId(String roomId) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        JSONObject jsonObject = JSONObject.parseObject(roomClient.getTimeSlotsByRoomId(roomId));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Object> someMethodToGetDateByRoomId(String roomId) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        JSONObject jsonObject = JSONObject.parseObject(roomClient.getDateByRoomId(roomId));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

    @Override
    public ResultPage<Object> methodToGetSeatInfoByDateAndTime(SeatViewReqDto seatViewReqDto) {
        // 设置当前用户信息到 UserContextHolder（通常在过滤器中完成）
        UserContext userContext = UserInfoContextHandler.getUserContext();
        if (ObjectUtil.isNull(userContext) || !userContext.getPermissions().contains("BOOK_STUDY_ROOM")) {
            //无权限直接返回 USER_NOT_PERSSIONS
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        JSONObject jsonObject = JSONObject.parseObject(roomClient.getSeatInfoByDateAndTime(seatViewReqDto));
        Object code = jsonObject.get("code");
        return switch (code.toString()) {
            case "A4010" -> ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
            default -> ResultPage.SUCCESS(jsonObject.get("data"));
        };
    }

}




