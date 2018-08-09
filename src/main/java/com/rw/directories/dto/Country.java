package com.rw.directories.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @ApiModelProperty(example = "1", required = true, value = "Уникальный идентификатор страны (записи)", dataType = "int")
    private int id;

    @ApiModelProperty(example = "21", required = true, value = "Код страны", dataType = "String")
    private String code;

}
