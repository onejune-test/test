package com.example.demo.config;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.zznode.dhmp.export.ExportContext;
import com.zznode.dhmp.export.support.filler.AbstractDataFiller;
import com.zznode.dhmp.export.utils.ExportHelper;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 描述
 *
 * @author 王俊
 */
@Configuration
public class EasyExcelExportConfiguration {



    static class EasyExcelDataFiller extends AbstractDataFiller {



        public EasyExcelDataFiller(ExportContext exportContext, ExportHelper exportHelper) {
            super(exportContext, exportHelper);
            Class<?> exportClass = exportContext.exportClass();
            ExcelWriterSheetBuilder sheet = EasyExcel.write().sheet();

            EasyExcel.writerSheet().build();
        }


        @Override
        protected void fillTitle(List<String> titles) {

        }

        @Override
        protected void fillRow(List<Object> objects) {

        }

        @Override
        public void flush(OutputStream outputStream) throws IOException {

        }
    }
}
