package com.rw.directories.controllers;

import com.rw.directories.dto.ErrorMessage;
import com.rw.directories.dto.PassengerCountry;
import com.rw.directories.services.PassengerCountryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value="pass-countries", description="Сервис получение данных из справочника государств выдачи документа, удостоверяющего личность пассажира", tags = "Справочник государств выдачи документа, удостоверяющего личность пассажира", basePath="/pass-countries")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerCountryController extends BaseController {
    @Autowired
    PassengerCountryService passengerCountryService;

    @RequestMapping(path="/${service.version}/directories/pass-countries", method = RequestMethod.GET)
    @ApiOperation(value = "Получение справочника всех государств выдачи документа, удостоверяющего личность пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class, responseContainer = "List"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    List<PassengerCountry> getPassengerCountries(@RequestParam @ApiParam(value="Язык ответа") String lang, @RequestHeader(name="IF-NONE-MATCH", required = false) @ApiParam(name="IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return passengerCountryService.getPassengerCountries(lang);
    }

}
