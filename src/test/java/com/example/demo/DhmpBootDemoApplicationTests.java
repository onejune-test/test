package com.example.demo;

import cn.hutool.core.util.RandomUtil;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.TestMapper;
import com.example.demo.service.ProductService;
import com.example.demo.service.TestService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatisflex.core.query.QueryWrapper;
import com.zznode.dhmp.context.util.ApplicationContextHelper;
import com.zznode.dhmp.core.constant.Province;
import com.zznode.dhmp.jdbc.datasource.DynamicDataSourceContextHolder;
import com.zznode.dhmp.schedule.DelegateExecuteJob;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.entity.table.ProductTableDef.PRODUCT;

@SpringBootTest
class DhmpBootDemoApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(DhmpBootDemoApplicationTests.class);

    @Test
    void contextLoads() {
        ProductMapper productMapper = ApplicationContextHelper.getContext().getBean(ProductMapper.class);
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {

            Product product = new Product();
            product.setGoodsName(productNames[RandomUtil.randomInt(5)]);
            product.setPrice(RandomUtil.randomDouble(0, 100, 4, RoundingMode.FLOOR));
            product.setProduceProvince(Province.currentProvinceCode());
            product.setPriceUnit(1);
            productList.add(product);
        }
//        productMapper.batchInsert(productList);
    }

    private static final String[] productNames = new String[]{"白菜", "萝卜", "橘子", "土豆", "安安"};


    @Test
    void testSelect() {

        TestMapper productMapper = ApplicationContextHelper.getContext().getBean(TestMapper.class);
        int product = productMapper.selectTest();
        System.out.println(product);
//        productMapper.selectListByQuery(QueryWrapper.create())
    }

    @Test
    void scheduleJob() {
        Scheduler scheduler = ApplicationContextHelper.getContext().getBean(Scheduler.class);
        JobKey jobKey = JobKey.jobKey("测试任务1", "default");
        JobDetail jobDetail = JobBuilder.newJob(DelegateExecuteJob.class)
                .withIdentity(jobKey)
                .requestRecovery(false)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(TriggerKey.triggerKey("测试任务1", "default"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .usingJobData("JOB_CODE", "TEST1")
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        System.out.println("执行完成。。。");
    }

    @Test
    void shutdownTask() {
        Scheduler scheduler = ApplicationContextHelper.getContext().getBean(Scheduler.class);
        JobKey jobKey = JobKey.jobKey("测试任务1", "default");
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey("测试任务1", "default"));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        System.out.println("暂停任务");
    }

    @Test
    void clearScheduler() throws SchedulerException {
        Scheduler scheduler = ApplicationContextHelper.getContext().getBean(Scheduler.class);
        scheduler.clear();
        System.out.println("清除");
    }

    @Test
    void testJpa() {

        ProductMapper productMapper = ApplicationContextHelper.getContext().getBean(ProductMapper.class);
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(PRODUCT.GOODS_NAME.like("1"));
        try (Page<Object> page = PageHelper.startPage(1, 10)) {
            List<Product> products = productMapper.selectListByQuery(queryWrapper);
            System.out.println(products);
        }

    }

    @Test
    void testProxy() {

        TestService testService = ApplicationContextHelper.getContext().getBean(TestService.class);
        testService.test();
        System.out.println("结束");
    }


    @Test
    void testSearch() {
        JdbcTemplate jdbcTemplate = ApplicationContextHelper.getContext().getBean(JdbcTemplate.class);

        DynamicDataSourceContextHolder.setDataSourceType("test");
        String sql = """
                select report_time,
                       'OLT'             as device_type,
                       max(olt_name)     as olt_name,
                       olt_ip,
                       sum(active_users) as active_users
                from ihgu_day_down_link_gw_audit
                WHERE report_time >= '2024-05-01'
                  and report_time <= '2024-05-14'
                  and olt_ip in (
                    '1.1.2.43'
                    )
                  and pon_id in (
                                 '299-39', '299-10', '299-11'
                    )
                group by report_time,
                         olt_ip""";
        long start = System.currentTimeMillis();
        List<Object> query = jdbcTemplate.query(sql, (rs, num) -> null);
        long end = System.currentTimeMillis();
        DynamicDataSourceContextHolder.clearDataSourceType();

        log.info("查询耗时：{} ms", end - start);
        System.out.println(query.size());
    }

    @Test
    void testSensitive(){
        ProductService productService = ApplicationContextHelper.getContext().getBean(ProductService.class);
        List<Product> productList = productService.productList();
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}
