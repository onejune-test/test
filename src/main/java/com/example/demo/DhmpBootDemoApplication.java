package com.example.demo;

import cn.hutool.core.util.RandomUtil;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.zznode.dhmp.core.constant.Province;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.RoundingMode;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableFeignClients
public class DhmpBootDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(DhmpBootDemoApplication.class, args);
        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
        String customValue = applicationContext.getEnvironment().getProperty("custom_value");
        System.out.println(customValue);
//        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
//        ResponseEntity<Object> forEntity = restTemplate.getForEntity("http://localhost:8080/v1/users", Object.class);
//        WebClient.RequestHeadersUriSpec<?> uriSpec = WebClient.create("http://localhost:8080/v1/users").get();

        ProductMapper productMapper = applicationContext.getBean(ProductMapper.class);
//        List<Product> productList = productMapper.selectList(Wrappers.emptyWrapper());
//        for (Product product : productList) {
//            System.out.println(product);
//        }
        Product product = new Product();
        product.setGoodsName(PRODUCT_NAMES[RandomUtil.randomInt(5)]);
        product.setPrice(RandomUtil.randomDouble(0, 100, 4, RoundingMode.FLOOR));
        product.setProduceProvince(Province.currentProvinceCode());
        product.setPriceUnit(1);
        productMapper.insertSelective(product);
        System.out.println(product);

    }

    private static final String[] PRODUCT_NAMES = new String[]{"白菜", "萝卜", "橘子", "土豆", "安安"};
}
