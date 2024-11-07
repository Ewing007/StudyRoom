package com.ewing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.UserTable;
import com.ewing.mapper.UserTableMapper;
import com.ewing.service.UserTableService;
import org.springframework.stereotype.Service;

/**
* @author ewing
* @description 针对表【user_table(用户表)】的数据库操作Service实现
* @createDate 2024-10-09 21:12:24
*/
@Service
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable>
    implements UserTableService {

}




