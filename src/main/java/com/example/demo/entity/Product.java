package com.example.demo.entity;

import com.example.demo.encrypt.annotation.Encrypt;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.zznode.dhmp.data.process.sensitive.annotation.SensitiveField;
import com.zznode.dhmp.data.process.sensitive.format.SensitiveFormatter;
import com.zznode.dhmp.export.annotation.ReportSheet;
import com.zznode.dhmp.lov.annotation.LovValue;
import com.zznode.dhmp.mybatis.flex.domain.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author 王俊
 * @date create in 2023/8/29
 */

@EqualsAndHashCode(callSuper = true)
@Table("demo_goods")
@Data
@ReportSheet(value = "测试", suffix = @ReportSheet.Suffix(suffixType = ReportSheet.SuffixType.DATE))
public class Product extends BaseModel<Product> {
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;
    @Encrypt
    @NotNull
    @SensitiveField(pattern = SensitiveFormatter.CHINESE_NAME)
    private String goodsName;
    private Double price;

    private String produceProvince;
    @LovValue(value = "PRICE_UNIT", titleField = "priceUnitMeaning")
    private Integer priceUnit;
    @Column(ignore = true)
    private String priceUnitMeaning;

}


