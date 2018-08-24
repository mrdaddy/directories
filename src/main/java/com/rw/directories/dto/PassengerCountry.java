package com.rw.directories.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Государство выдачи документа, удостоверяющего личность пассажира")
public class PassengerCountry extends Directory {
    @ApiModelProperty(example = "Гонконг", required = true, value = "Наименование государства на указанном языке", dataType = "String")
    private String name;

    @ApiModelProperty(example = "HKG", required = true, value = "Код ISO государства", dataType = "String")
    private String isoCode;
}
