package com.rw.directories.controllers;

import com.rw.directories.dto.ErrorMessage;
import com.rw.directories.dto.Parameter;
import com.rw.directories.services.ParameterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value="parameters", description="Сервис получение данных из справочника параметров СППД", tags = "Справочник параметров СППД", basePath="/directories")
@RequestMapping(path = "/${service.version}/directories/parameters", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParameterController extends BaseController {
    @Autowired
    ParameterService parameterService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Получение справочника параметров СППД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    public List<Parameter> getParameters(@RequestHeader(name = "IF-NONE-MATCH", required = false) @ApiParam(name = "IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return parameterService.getParameters();
    }

    @RequestMapping(path = "/${service.version}/directories/parameters/{code}", method = RequestMethod.GET)
    @ApiOperation(value = "Параметр СППД по коду")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class, responseContainer = "List"),
    })
    public Parameter getParameterByCode(@PathVariable("code") @ApiParam(value = "Код параметра") String code,
                                 @RequestHeader(name = "IF-NONE-MATCH", required = false) @ApiParam(name = "IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return parameterService.getParameter(code);
    }


}







