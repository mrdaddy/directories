package com.rw.directories.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
    @NotNull
    @ApiModelProperty(example = "1", required = true, value = "Уникальный идентификатор параметра (записи)", dataType = "int")
    private Integer id;

    @NotNull
    @ApiModelProperty(example = "T", required = true, allowableValues = "E, G, M, P, R, S, T", value = "Тип параметра  (код)", dataType = "String", notes="G – общие параметры, S – параметры для получения расписания, T – параметры для покупки билетов,\tR – параметры для возврата,\tP – параметры для взаимодействия с платежными системами, E – параметры для взаимодействия с “ССПД-Экспресс”, M – параметры для отправки писем, U - параметры для ведения пользователей")
    private String category;

    @NotNull
    @ApiModelProperty(example = "TICKET_PASSENGER_COUNT", required = true, value = "Код параметра  (код)", dataType = "String")
    private String code;
    @ApiModelProperty(example = "4", required = true, allowEmptyValue = true, value = "Значение параметра", dataType = "String")
    private String value;
}
