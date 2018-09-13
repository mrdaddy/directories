package com.rw.directories.dto;

import com.rw.directories.BooleanTransformer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import lombok.experimental.Accessors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Платежные сисетемы из справочника платежных систем, с помощью которых можно производить покупку проездныех документов")
public class PaymentSystem extends Directory {

    @ApiModelProperty(example = "ERIP", required = true, value = "Система оплаты", dataType = "String")
    private String type;

    @ApiModelProperty(example = "Платежная система Рассчёт", required = true, value = "Полное название системы оплаты на языке, указанном в параметре lang", dataType = "String")
    private String name;

    @ApiModelProperty(example = "ЕРИП", required = true, value = "Краткое название системы оплаты на языке, указанном в параметре lang", dataType = "String")
    private String shortName;

    @ApiModelProperty(example = "true", allowableValues = "true, false", required = true, value = "Признак, указывающий, является ли платежная система системой по умолчанию ", dataType = "boolean")
    private boolean isDefault;

    @ApiModelProperty(example = "http://93.84.121.178/wps/PA_eTicketInquire/IbbExample", required = false, value = "Адрес (URL) платежной системы для оплаты заказа", dataType = "String")
    private String url;

    @ApiModelProperty(example = "30", required = true, value = "Время (в минутах), отведенное на оплату заказа (по умолчанию - 20 минут)", dataType = "int")
    private int time;

    @ApiModelProperty(example = "100100111", required = false, value = "Код (идентификатор) системы продажи проездных документов в платежной системе", dataType = "String")
    private String systemPayCode;

    @ApiModelProperty(example = "poezd.rw.by", required = false, value = "Название системы продажи проездных документов в платежной системе (для отображения на страничке оплаты)", dataType = "String")
    private String systemPayName;

    @ApiModelProperty(example = "https://93.84.121.178/wps/PA_eTicketInquire/PaymentTicketResultAssist?status=0", required = false, value = "Адрес (URL) системы проездных документов для возврата в случае успешной оплаты", dataType = "String")
    private String systemPayUrl;

    @ApiModelProperty(example = "https://93.84.121.178/wps/PA_eTicketInquire/PaymentTicketResultAssist?status=0", required = false, value = "Адрес (URL) системы проездных документов для возврата в случае неуспешной оплаты", dataType = "String")
    private String systemPayCancelUrl;

    @ApiModelProperty(example = "check_agent =<true/false>", required = false, value = "Дополнительные параметры, специфичные для конкретной платежной системы", dataType = "String")
    private String additionalParams;

    @ApiModelProperty(example = "true", required = true, value = "Является ли платежная система доступной  для английского варианта странички выбора системы оплаты", dataType = "boolean")
    private boolean isAccessibleEN;

    @ApiModelProperty(example = "true", required = true, value = "Является ли платежная система доступной  для русского варианта странички выбора системы оплаты", dataType = "boolean")
    private boolean isAccessibleRU;

    @ApiModelProperty(example = "payment string 123", required = false, value = "Идентификатор платежного агента", dataType = "String")
    private String payingAgent;

    @ApiModelProperty(example = "true", required = true, value = "Признак, указывающий, возможен ли возврат проездных документов через платежную системут", dataType = "boolean")
    private boolean isTicketReturnUnable;

    @ApiModelProperty(example = "true", required = true, value = "Признак, указывающий, возможен ли возврат проездных документов через платежную систему в режиме on-line", dataType = "boolean")
    private boolean isOnlineReturnUnable;


    public void setDefaultFromString(String aDefault) {

        BooleanTransformer booleanTransformer = new BooleanTransformer();
        isDefault = booleanTransformer.transformToBoolean(aDefault);
    }

    public void setAccessibleENFromString(String accessibleEN) {
        BooleanTransformer booleanTransformer = new BooleanTransformer();
        isAccessibleEN = booleanTransformer.transformToBoolean(accessibleEN);
    }

    public void setAccessibleRUFromString(String accessibleRU) {
        BooleanTransformer booleanTransformer = new BooleanTransformer();
        isAccessibleRU = booleanTransformer.transformToBoolean(accessibleRU);
    }

    public void setTicketReturnUnableFromString(String ticketReturnUnable) {
        BooleanTransformer booleanTransformer = new BooleanTransformer();
        isTicketReturnUnable = booleanTransformer.transformToBoolean(ticketReturnUnable);
    }

    public void setOnlineReturnUnableFromString(String onlineReturnUnable) {
        BooleanTransformer booleanTransformer = new BooleanTransformer();
        isOnlineReturnUnable = booleanTransformer.transformToBoolean(onlineReturnUnable);
    }
}

