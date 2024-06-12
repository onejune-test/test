package com.example.demo.task;

import com.zznode.dhmp.context.annotation.ProvinceComponent;
import com.zznode.dhmp.core.constant.Province;
import com.zznode.dhmp.schedule.AbstractJobHandler;
import org.quartz.JobExecutionContext;

/**
 * 描述
 *
 * @author 王俊
 */
//@JobHandler("TEST2")
@ProvinceComponent(provinces = Province.JI_LIN)
public class Test2Task extends AbstractJobHandler {
    @Override
    protected void runInternal(JobExecutionContext context) {

        logger.info("running test2");
    }
}
