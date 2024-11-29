package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.CheckInTable;
import generator.service.CheckInTableService;
import generator.mapper.CheckInTableMapper;
import org.springframework.stereotype.Service;

/**
* @author ewing
* @description 针对表【check_in_table(自习室签到表)】的数据库操作Service实现
* @createDate 2024-11-28 18:10:30
*/
@Service
public class CheckInTableServiceImpl extends ServiceImpl<CheckInTableMapper, CheckInTable>
    implements CheckInTableService{

}




