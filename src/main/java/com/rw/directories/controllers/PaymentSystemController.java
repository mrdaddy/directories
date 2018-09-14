package com.rw.directories.controllers;

import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.services.PaymentSystemService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "pay-systems", description = "Сервис получение данных из справочника платежных систем,  с помощью которых можно производить покупку проездныех документов", tags = "Справочник платежных систем, с помощью которых можно производить покупку проездныех документов", basePath = "/pay-systems")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentSystemController extends BaseController {

    @Autowired
    PaymentSystemService paymentSystemService;

    @RequestMapping(path = "/${service.version}/directories/pay-systems", method = RequestMethod.GET)
    @ApiOperation(value = "Получение справочника платежных систем, с помощью которых можно производить покупку проездныех документов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    List<PaymentSystem> getPaymentSystem (@RequestParam @ApiParam(value = "Язык ответа") String lang, @RequestHeader(name = "IF-NONE-MATCH", required = false) @ApiParam(name = "IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return paymentSystemService.getPaymentSystem(lang);
    }
}

