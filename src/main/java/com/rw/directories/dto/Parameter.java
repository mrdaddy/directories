package com.rw.directories.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Параметр СППД")
public class Parameter extends Directory {
    public enum CATEGORY {E, G, M, P, R, S, T, U}

    @ApiModelProperty(example = "T", required = true, allowableValues = "E, G, M, P, R, S, T, U", value = "Тип параметра - код. Значения: " +
            "G – общие параметры, " +
            "S – параметры для получения расписания, " +
            "T – параметры для покупки билетов, " +
            "R – параметры для возврата, " +
            "P – параметры для взаимодействия с платежными системами, " +
            "E – параметры для взаимодействия с “ССПД-Экспресс”, " +
            "M – параметры для отправки писем, " +
            "U - параметры для ведения пользователей", dataType = "String")
    private CATEGORY category;

    @ApiModelProperty(example = "TICKET_PASSENGER_COUNT", required = true, value = "Код параметра в СППД", dataType = "String")
    private String code;

    @ApiModelProperty(example = "4", required = true, allowEmptyValue = true, value = "Значение параметра", dataType = "String")
    private String value;
}
