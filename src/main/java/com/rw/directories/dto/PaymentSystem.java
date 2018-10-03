package com.rw.directories.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Платежные сисетемы из справочника платежных систем, с помощью которых можно производить покупку проездныех документов")
public class PaymentSystem extends Directory {
    public enum PAYMENT_SYSTEM {BB_IB, ERIP, BB_IA}

    @ApiModelProperty(example = "ERIP", required = true, value = "Система оплаты - код", dataType = "String")
    private PAYMENT_SYSTEM type;

    @ApiModelProperty(example = "Платежная система Рассчёт", required = true, value = "Полное название системы оплаты на языке, указанном в параметре lang", dataType = "String")
    private String name;

    @ApiModelProperty(example = "ЕРИП", required = true, value = "Краткое название системы оплаты на языке, указанном в параметре lang", dataType = "String")
    private String shortName;

    @ApiModelProperty(example = "true", allowableValues = "true, false", required = true, value = "Признак, указывающий, является ли платежная система системой по умолчанию ", dataType = "boolean")
    private boolean isDefault;

    @ApiModelProperty(example = "20", required = true, value = "Время (в минутах), отведенное на оплату заказа (по умолчанию - 20 минут)", dataType = "int")
    private int paymentTime;

    @ApiModelProperty(example = "100100111", required = false, value = "Код (идентификатор) системы продажи проездных документов в платежной системе", dataType = "String")
    private String eticketCode;

    @ApiModelProperty(example = "poezd.rw.by", required = false, value = "Название системы продажи проездных документов в платежной системе (для отображения на странице оплаты)", dataType = "String")
    private String eticketName;

    @ApiModelProperty(example = "https://93.84.121.178/wps/PA_eTicketInquire/PaymentTicketResultAssist?status=0", required = false, value = "Адрес (URL) системы проездных документов для возврата в случае успешной оплаты", dataType = "String")
    private String paymentSuccessUrl;

    @ApiModelProperty(example = "https://93.84.121.178/wps/PA_eTicketInquire/PaymentTicketResultAssist?status=0", required = false, value = "Адрес (URL) системы проездных документов для возврата в случае неуспешной оплаты", dataType = "String")
    private String paymentErrorUrl;

  /*  @ApiModelProperty(example = "payment string 123", required = false, value = "Идентификатор платежного агента", dataType = "String")
    private String payingAgent;
*/
    @ApiModelProperty(example = "true", required = true, value = "Признак, указывающий, возможен ли возврат проездных документов через платежную системут", dataType = "boolean")
    private boolean isTicketReturnEnabled;

    @ApiModelProperty(example = "true", required = true, value = "Признак, указывающий, возможен ли возврат проездных документов через платежную систему в режиме on-line", dataType = "boolean")
    private boolean isOnlineReturnEnabled;

    @ApiModelProperty(example = "true", required = true, value = "Признак, указывающий, является ли платёж тестовым (используется для передачи в качестве параметры в системе Ассист)", dataType = "boolean")
    private boolean isTest;
}

