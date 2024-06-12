package com.example.demo.controller;

import com.example.demo.encrypt.annotation.Encrypt;
import com.example.demo.entity.Product;
import com.example.demo.service.TestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zznode.dhmp.core.constant.BaseConstants;
import com.zznode.dhmp.core.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述
 *
 * @author 王俊
 * @date create in 2023/8/29
 */
@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:18080", originPatterns = "*", allowCredentials = "true")
@Slf4j
public class TestController {

    private final TestService testService;


    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public ResponseEntity<?> testException(String a) {
        if (Objects.equals(a, "a")) {
            throw new CommonException(BaseConstants.ErrorCode.ERROR_CODE_REPEAT);
        }
        log.info("MAIN,{}, virtual: {}", Thread.currentThread(), Thread.currentThread().isVirtual());
        testService.test();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> testDelete(@RequestParam ArrayList<String> snList, HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return ResponseEntity.ok(snList);
    }

    @PostMapping("/test-post")
    public ResponseEntity<?> testPost(@Validated Product product) {
        System.out.println(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> testAuth(String token) {
//        throw new CommonException(BaseConstants.ErrorCode.ERROR_CODE_REPEAT);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("username", "lisi");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/encrypt")
    @Encrypt
    public ResponseEntity<?> testEncrypt(String token) {

        return ResponseEntity.ok(new Product().limit(1).one());
    }

    @PostMapping("/handsOperation")
    public ResponseEntity<?> testHandsOperation(@RequestBody String xml) {
        log.info(String.format("xml:%s", xml));
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.xml().build();
        ClassPathResource classPathResource = new ClassPathResource("onuState.xml");
        try (InputStream inputStream = classPathResource.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
