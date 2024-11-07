package com.ewing.job;

import com.ewing.domain.dto.AnnouncementDto;
import com.ewing.domain.entity.AnnouncementTable;
import com.ewing.mapper.AnnouncementTableMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author: Ewing
 * @Date: 2024-10-15-16:28
 * @Description:
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AnnouncementExpireJob {



    private final AnnouncementTableMapper  announcementMapper;

    @XxlJob("announcementExpireJobHandler")
    public void executeAnnouncementExpireJob(String param) {
        log.info("AnnouncementExpireJob执行开始");
        log.info("执行参数：" + param);

        // 正确的类型
        List<AnnouncementTable> expiredAnnouncements = announcementMapper.selectActiveAnnouncements();

        // 更新公告状态
        if (expiredAnnouncements != null && !expiredAnnouncements.isEmpty()) {
            for (AnnouncementTable announcement : expiredAnnouncements) {
                announcementMapper.updateAnnouncementStatusToExpired(announcement.getId());
            }
            log.info("已更新 " + expiredAnnouncements.size() + " 条公告为已过期。");
        } else {
            log.info("没有需要更新为已过期的公告。");
        }

        log.info("AnnouncementExpireJob执行结束");

    }
}



