package com.ewing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.entity.UserRoles;
import com.ewing.mapper.UserRolesMapper;
import com.ewing.service.UserRolesService;
import org.springframework.stereotype.Service;

/**
* @author ewing
* @description 针对表【user_roles(用户角色关联表)】的数据库操作Service实现
* @createDate 2024-10-13 19:43:37
*/
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles>
    implements UserRolesService {

}




