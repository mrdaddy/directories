package com.rw.directories.controllers;

import com.rw.directories.dto.Parameter;
import com.rw.directories.services.ParameterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="parameters", description="Сервис получение данных из справочника параметров СППД", tags = "Справочник параметров СППД", basePath="/directories")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ParameterController {
    @Autowired
    ParameterService parameterService;

    @RequestMapping(path="/${service.version}/directories/parameters", method = RequestMethod.GET)
    @ApiOperation(value = "Список всех параметров")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag",
                                    response = String.class)})
    })
    List<Parameter> getParameters() {
        return parameterService.getParameters();
    }

    @RequestMapping(path="/${service.version}/directories/parameter/{code}", method = RequestMethod.GET)
    @ApiOperation(notes = "Сервис получения параметра по коду", value = "Параметр по коду")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag",
                                    response = String.class)})
    })
    Parameter getParameterByCode(@PathVariable("code") @ApiParam(value="Код параметра") String code) {
        return parameterService.getParameter(code);
    }

}
