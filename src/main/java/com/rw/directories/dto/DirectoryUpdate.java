package com.rw.directories.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryUpdate {
    @ApiModelProperty(example = "Parameters", required = true, allowableValues = "Parameters, Countries, PassengerCountries, DocumentTypes", value = "Наименование справочника", dataType = "String", notes="Parameters – общие параметры, S – параметры для получения расписания, T – параметры для покупки билетов,\tR – параметры для возврата,\tP – параметры для взаимодействия с платежными системами, E – параметры для взаимодействия с “ССПД-Экспресс”, M – параметры для отправки писем, U - параметры для ведения пользователей")
    private String directory;

    @ApiModelProperty(example = "2018-08-13T11:44:31.881+0300", required = true, value = "Дата последнего обновления справочника, часовой пояс Europe/Minsk", dataType = "datetime")
    private Date lastUpdatedOn;

}
