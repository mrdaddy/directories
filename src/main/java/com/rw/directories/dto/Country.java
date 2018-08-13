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
public class Country extends Directory{


    @ApiModelProperty(example = "1", required = true, value = "Уникальный идентификатор страны (записи)", dataType = "int")
    private int id;

    @ApiModelProperty(example = "21", required = true, value = "Код страны", dataType = "String")
    private String code;

    @ApiModelProperty(example = "БЕЛАРУСЬ", required = true, value = "Название страны на указанном языке", dataType = "String")
    private String name;

    @ApiModelProperty(example = "true", allowableValues = "true, false", required = true, value = "Признак, указывающий, продаются ли в данную страну проездные документы", dataType = "boolean")
    private boolean ticketSellingAllowed;

    @ApiModelProperty(example = "100", required = false, value = "Приоритет по умолчанию станций данной страны для выбора", dataType = "int")
    private int stationSelectPriority;

    @ApiModelProperty(example = "Europe/Minsk", required = false, value = "Часовой пояс страны", dataType = "String")
    private String timeZone;

    @ApiModelProperty(example = "Время отправления местное", required = false, value = "Сообщение о времени отправления на языке, указанном в параметре lang", dataType = "String")
    private String departureMsg;

    @ApiModelProperty(example = "Время прибытия местное", required = false, value = "Сообщение о времени прибытия на языке, указанном в параметре lang", dataType = "String")
    private String arrivalMsg;

    @ApiModelProperty(example = "true", required = true, value = "Признак, указывающий, как осуществляется продажа проездных документов (билетов) – по “глобальной цене” или нет", dataType = "boolean")
    private boolean globalPrice;

    @ApiModelProperty(example = "true", required = true, value = "Максимальный возраст ребенка для проезда по безденежному тарифу", dataType = "int")
    private int freeTicketAge;

    @ApiModelProperty(example = "true", required = true, value = "Максимальный возраст ребенка для проезда по детскому тарифу", dataType = "int")
    private int childrenTicketAge;

}
