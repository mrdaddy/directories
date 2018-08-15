package com.rw.directories.controllers;

import com.rw.directories.dto.Country;
import com.rw.directories.services.CountryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="countries", description="Сервис получение данных из справочника государств, в которые могут продаваться проездные документы", tags = "Справочник государств, в которые могут продаваться проездные документы", basePath="/countries")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

    @Autowired
    CountryService countryService;

    @RequestMapping(path="/${service.version}/directories/countries", method = RequestMethod.GET)
    @ApiOperation(value = "Список всех государств")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag",
                                    response = String.class)})
    })
    List<Country> getCountries(@RequestParam @ApiParam(value="Язык ответа") String lang) {
        return countryService.getCountries(lang);
    }

}
