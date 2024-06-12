package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zznode.dhmp.core.constant.BaseConstants;
import com.zznode.dhmp.core.constant.Province;
import com.zznode.dhmp.export.annotation.ReportColumn;
import com.zznode.dhmp.export.annotation.ReportSheet;
import com.zznode.dhmp.mybatis.flex.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 描述
 *
 * @author 王俊
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ReportSheet(value = "测试", suffix = @ReportSheet.Suffix(suffixType = ReportSheet.SuffixType.DATE))
public class ProductExport extends BaseModel<ProductExport> {
    @ReportColumn(value = "id", pattern = "##")
    private Long id;
    @ReportColumn(value = "产品名称")
    private String goodsName;
    @ReportColumn(value = "单价")
    private Double price;

    @ReportColumn(value = "产地",
            enumConvert = @ReportColumn.EnumConvert(enumClass = Province.class, enumGetName = "provinceCode", enumValueName = "provinceName"))

    private String produceProvince;

//    @LovValue(value = "PRICE_UNIT", titleField = "priceUnitMeaning")
    @ReportColumn(value = "单位", lovCode = "PRICE_UNIT")
    private Integer priceUnit;

//    @ReportColumn(value = "单位")
    private String priceUnitMeaning;

    @ReportColumn(value = "创建时间", pattern = BaseConstants.Pattern.DATETIME, replaceField = "creationDate")
    private Date createdDate;

    @ReportColumn(value = "上架时间", pattern = BaseConstants.Pattern.DATETIME)
    private LocalDateTime lastUpdate;
    /**
     * 模拟报错
     */
    @ReportColumn(value = "上架时间2", pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdatedDate;
}
