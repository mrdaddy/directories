package com.rw.directories.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Список справочников с временем последнего обновления")
public class DirectoryUpdate {
    public enum DIRECTORY {Parameters, Countries, PassengerCountries, DocumentTypes, PaymentSystems}
    @ApiModelProperty(example = "Parameters", required = true, value = "Наименование справочника", dataType = "String")
    private DIRECTORY directory;

    @ApiModelProperty(example = "2018-08-13 11:44:31.8810300", required = true, value = "Дата последнего обновления справочника, часовой пояс Europe/Minsk", dataType = "datetime")
    private Timestamp lastUpdatedOn;

}
