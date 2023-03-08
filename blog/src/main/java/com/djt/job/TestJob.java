package com.djt.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {
    @Scheduled(cron = "0/5 * * * * ?")
    //表示此方法为定时任务q
    public void testJob(){
        //每隔十分钟，将redis数据库中的阅读量信息更新至mysql中
//        System.out.println("定时任务开始了");

    }

}
