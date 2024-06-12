package com.example.demo.task;


import com.zznode.dhmp.context.annotation.ProvinceComponent;
import com.zznode.dhmp.core.constant.Province;
import com.zznode.dhmp.schedule.AbstractJobHandler;
import com.zznode.dhmp.schedule.annotation.JobHandler;
import org.quartz.JobExecutionContext;

@JobHandler(value = "TEST11", name = "测试任务")
//可以配合ProvinceComponent使用
@ProvinceComponent(provinces = Province.JI_LIN)
public class TestTask extends AbstractJobHandler {

    @Override
    protected void runInternal(JobExecutionContext context) {
        logger.info("running test1");
//        throw new RuntimeException("test1 execution");
    }
}
