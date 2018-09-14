package com.rw.directories.controllers;

import com.rw.directories.dto.Country;
import com.rw.directories.services.CountryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value="countries", description="Сервис получение данных из справочника государств, в которые могут продаваться проездные документы", tags = "Справочник государств, в которые могут продаваться проездные документы", basePath="/countries")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController extends BaseController {

    @Autowired
    CountryService countryService;

    @RequestMapping(path="/${service.version}/directories/countries", method = RequestMethod.GET)
    @ApiOperation(value = "Получение справочника государств, в которые могут продаваться проездные документы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })

    List<Country> getCountries(@RequestParam @ApiParam(value="Язык ответа") String lang, @RequestHeader(name="IF-NONE-MATCH", required = false) @ApiParam(name="IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) throws EmptyResultDataAccessException {
        return countryService.getCountries(lang);
    }
}
