package com.ewing.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.ewing.domain.dto.UserDto;
import com.ewing.domain.entity.UserTable;

import com.ewing.mapper.UserTableMapper;
import constant.CacheConstant;
import constant.DataBaseConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @Author: Ewing
 * @Date: 2024-06-30-15:35
 * @Description:
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserInfoCacheManager {

    private final UserTableMapper userTableMapper;

    @Cacheable(cacheManager = CacheConstant.REDIS_CACHE_MANAGER, cacheNames = CacheConstant.USER_INFO_CACHE_NAME)
    public UserDto getUser(String userId) {
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.UserInfoTable.COLUMN_USERID, userId);
        UserTable userInfo = userTableMapper.selectOne(queryWrapper.last(DataBaseConstant.sqlEnum.LIMIT_1.getSql()));
        if(ObjectUtil.isNull(userInfo)) {
            return null;
        }

        return UserDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .avatar(userInfo.getAvatar())
                .creditScore(userInfo.getCreditScore())
                .email(userInfo.getEmail())
                .sex(userInfo.getSex())
                .build();
    }
}
