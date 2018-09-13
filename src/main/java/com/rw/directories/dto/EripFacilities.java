package com.rw.directories.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Платежные инструменты системы “Расчет“")
public class EripFacilities extends Directory {

    @ApiModelProperty(example = "IB", required = true, value = "Тип платежного инструмента.", dataType = "String")
    private String type;

    @ApiModelProperty(example = "Интернет-банкинг ОАО “Белагропромбанк“", required = true, value = "Название платежного инструмента", dataType = "String")
    private String name;

    @ApiModelProperty(example = "https://www.ibank.belapb.by/", required = true, value = "URL платежного инструмента", dataType = "String")
    private String url;
}
