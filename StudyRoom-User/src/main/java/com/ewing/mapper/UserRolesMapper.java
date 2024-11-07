package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.entity.UserRoles;

import java.util.List;


/**
* @author ewing
* @description 针对表【user_roles(用户角色关联表)】的数据库操作Mapper
* @createDate 2024-10-13 19:43:37
* @Entity generator.domain.UserRoles
*/
public interface UserRolesMapper extends BaseMapper<UserRoles> {
    /**
     * 根据用户ID获取所有角色名称
     *
     * @param userId 用户ID
     * @return 角色名称列表
     */
    List<String> getRolesByUserId(String userId);
}




