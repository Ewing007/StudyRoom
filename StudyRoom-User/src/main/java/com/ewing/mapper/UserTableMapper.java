package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ewing.domain.entity.UserTable;
import org.apache.ibatis.annotations.Param;

/**
* @author ewing
* @description 针对表【user_table(用户表)】的数据库操作Mapper
* @createDate 2024-10-09 21:12:24
* @Entity generator.domain.UserTable
*/
public interface UserTableMapper extends BaseMapper<UserTable> {
    void updateCreditScoreByUserId(@Param("userId") String userId, @Param("creditScore") Integer creditScore);

    void deleteUserRolesByUserId(@Param("userId") String userId);

    void insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    void deleteUserPermissionsByUserId(@Param("userId") String userId);

    void insertUserPermission(@Param("userId") String userId, @Param("permissionId") String permissionId);

}




