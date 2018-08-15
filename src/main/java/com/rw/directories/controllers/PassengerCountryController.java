package com.rw.directories.controllers;

import com.rw.directories.dto.PassengerCountry;
import com.rw.directories.services.PassengerCountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="pass-countries", description="Сервис получение данных из справочника государств выдачи документа, удостоверяющего личность пассажира", tags = "Справочник государств выдачи документа, удостоверяющего личность пассажира", basePath="/pass-countries")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerCountryController {
    @Autowired
    PassengerCountryService passengerCountryService;

    @RequestMapping(path="/${service.version}/directories/pass-countries", method = RequestMethod.GET)
    @ApiOperation(value = "Список всех государств")
    List<PassengerCountry> getCountries(@RequestParam String lang) {
        return passengerCountryService.getPassengerCountries(lang);
    }

}
