package com.rw.directories.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class Parameter {
    @ApiModelProperty(example = "T", required = true, allowableValues = "E, G, M, P, R, S, T", value = "Категория параметров", dataType = "String")
    private String category;
    @ApiModelProperty(example = "TICKET_PASSENGER_COUNT", required = true, value = "Код параметра", dataType = "String")
    private String code;
    @ApiModelProperty(example = "4", required = true, allowEmptyValue = true, value = "Значение", dataType = "String")
    private String value;

}
