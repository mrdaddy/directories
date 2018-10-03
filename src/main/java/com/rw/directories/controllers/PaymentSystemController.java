package com.rw.directories.controllers;

import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.services.PaymentSystemService;
import com.rw.directories.utils.LanguageUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "ps", description = "Сервис получения списка платежных систем, с помощью которых можно производить оплату проездныех документов", tags = "Справочник платежных систем, с помощью которых можно производить покупку проездныех документов", basePath = "/ps")
@RequestMapping(path = "/${service.version}/directories/ps", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentSystemController extends BaseController {

    @Autowired
    PaymentSystemService paymentSystemService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Получение справочника платежных систем, с помощью которых можно производить покупку проездных документов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    public List<PaymentSystem> getPaymentSystem (@RequestParam @ApiParam(value="Язык ответа", required = true) LanguageUtils.SUPPORTED_LANGUAGES lang,
                                          @RequestHeader(name = "IF-NONE-MATCH", required = false) @ApiParam(name = "IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return paymentSystemService.getPaymentSystem(lang.toString());
    }
}

