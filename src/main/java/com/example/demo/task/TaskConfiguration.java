package com.example.demo.task;

import com.zznode.dhmp.schedule.annotation.EnableDhmpScheduling;
import org.quartz.Scheduler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述
 *
 * @author 王俊
 */
@Configuration
@EnableDhmpScheduling
public class TaskConfiguration implements InitializingBean {

    private final Scheduler scheduler;

    public TaskConfiguration(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void afterPropertiesSet() throws Exception {


    }
}
