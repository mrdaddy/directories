package com.rw.directories.dto;

import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentType extends Directory {

    @ApiModelProperty(example = "ПБ", required = true, value = "Код типа документа в СППД", dataType = "String")
    private String code;

    @ApiModelProperty(example = "Паспорт Беларуси", required = true, value = "Название типа документа на указанном языке", dataType = "String")
    private String name;

    @ApiModelProperty(example = "A", required = true, allowableValues = "A, R", value = "Состояние типа документа (код)", dataType = "String", notes="A – активный, R – помечен для удаления. При получении списка типов документов для выбора участвуют типы документов с состоянием A")
    private String status;

    @ApiModelProperty(example = "7", required = true, allowableValues = "1,2,3,4,5,6,7", value = "Признак, указывающий, для каких типов проездных документов (билетов) должен показываться для выбора данный  тип документа при покупке", dataType = "int", notes="Задается как сумма значений: 1 - Взрослый, 2 - Ребенок с местом, 4 - Ребенок без места.")
    private int useForET;

    @ApiModelProperty(example = "true", required = true, allowableValues = "true, false", value = "Признак, указывающий, показывается ли данный тип документа при покупке проездного документа “по глобальной цене”", dataType = "boolean")
    private boolean isUsedForGlobalPrice;

    @ApiModelProperty(example = "", required = true, allowableValues = "true, false", value = "Код типа документа в АСУ “Экспресс”", dataType = "String")
    private String expressCode;

}
