package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductExport;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.ProductService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Row;
import com.zznode.dhmp.data.page.domain.PageRequest;
import com.zznode.dhmp.data.page.domain.PageResult;
import com.zznode.dhmp.export.ExportContext;
import com.zznode.dhmp.export.annotation.Export;
import com.zznode.dhmp.export.support.exporter.Exporter;
import com.zznode.dhmp.export.support.exporter.ExporterFactory;
import com.zznode.dhmp.export.web.mvc.function.HandlerFunctionExportContextFactory;
import com.zznode.dhmp.file.FileClient;
import com.zznode.dhmp.file.FileInfo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author 王俊
 * @date create in 2023/8/29
 */
@RequestMapping("/products")
@RestController
@Slf4j
public class ProductController {

    private final ProductMapper productMapper;
    private final SqlSessionFactory sqlSessionFactory;
    private final ProductService productService;
    private final ExporterFactory exporterFactory;

    private final FileClient fileClient;

    public ProductController(ProductMapper productMapper,
                             SqlSessionFactory sqlSessionFactory, ProductService productService, ExporterFactory exporterFactory, FileClient fileClient) {
        this.productMapper = productMapper;
        this.sqlSessionFactory = sqlSessionFactory;
        this.productService = productService;
        this.exporterFactory = exporterFactory;
        this.fileClient = fileClient;
    }

    @GetMapping
    public ResponseEntity<?> page(@SortDefault(sort = "price", direction = Sort.Direction.DESC) PageRequest pageRequest) {


//        Page<Product> paginate = productMapper.paginate(pageRequest.getPageNumber(), pageRequest.getPageSize(), QueryWrapper.create());

        QueryWrapper queryWrapper = QueryWrapper.create();
        Sort sort = pageRequest.getSort();
        sort.stream().forEach(order -> queryWrapper.orderBy(order.getProperty(), order.getDirection().isAscending()));
        PageResult<Product> result = pageRequest.doPage(() -> productMapper.selectListByQuery(queryWrapper));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test-download")
    public void testDownLoad(HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.addHeader("Content-Disposition", "attachment;filename=apache-maven-3.6.3-bin.zip");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            FileUtil.writeToStream(new File("D:\\MyFile\\software\\apache-maven-3.6.3-bin.zip"), outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/export")
    @Export(ProductExport.class)
    public ResponseEntity<List<ProductExport>> export(ExportContext exportContext, Sort sort) {
        return ResponseEntity.ok(productService.list());
    }
    @GetMapping("/exportService")
    public void exportService() {
        productService.list();
    }

    @GetMapping("/export-map")
    @Export(ProductExport.class)
    public ResponseEntity<List<?>> exportMap(ExportContext exportContext) {
        List<Row> list = productMapper.selectRowsByQuery(QueryWrapper.create().orderBy("id").limit(1000));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/download")
    public void downloadBig(HttpServletResponse response) {
//        fileClient.download("test", "/testpath/Chat2DB-Setup-3.0.7.exe", response, "Chat2DB-Setup-3.0.7.exe");
    }

    @GetMapping("/template/{code}")
    public void downloadTemplate(HttpServletResponse response, @PathVariable String code) {
        log.info("templateId: {}", code);
        InputStream inputStream = fileClient.getFile(code);
        String string = IoUtil.read(inputStream).toString();
        System.out.println(string);
    }

    @PostMapping("/import")
    public ResponseEntity<?> importProducts(@RequestParam("file") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String objectName = "/testpath/abc/" + originalFilename;
        try {
            FileInfo fileInfo = fileClient.saveFile("test", objectName, multipartFile.getInputStream(), true);
            Map<String, Object> result = new HashMap<>();
            result.put("url", "");
            result.put("id", fileInfo.getUid());
            result.put("snNum", 11);
            result.put("fileInfo", fileInfo);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/stream")
    public ResponseEntity<StreamingResponseBody> stream() {
        long start = System.currentTimeMillis();
        System.out.printf("%s - 开始时间：%d%n", Thread.currentThread().getName(), start);
        // 内部执行还是用的系统内部的线程池
        StreamingResponseBody stream = outputStream -> {
            outputStream.write(("当前时间: " + System.currentTimeMillis() + "<br/>").getBytes());
            ExportContext exportContext = new HandlerFunctionExportContextFactory(ProductExport.class).createContext();
            Exporter exporter = exporterFactory.createExporter(exportContext);
            List<ProductExport> list = productService.list();
            long exportStart = System.nanoTime();
            exporter.export(list);
            long exportEnd = System.nanoTime();
            if (log.isDebugEnabled()) {
                log.debug(String.format("export data cost %s ms", Duration.ofNanos(exportEnd - exportStart).toMillis()));
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html;charset=UTF-8");
        ResponseEntity<StreamingResponseBody> response = new ResponseEntity<>(stream, headers, HttpStatus.OK);

        long end = System.currentTimeMillis();
        System.out.printf("%s - 结束时间：%d%n", Thread.currentThread().getName(), end);
        System.out.printf("总耗时：%d毫秒%n", (end - start));
        return response;
    }
}
