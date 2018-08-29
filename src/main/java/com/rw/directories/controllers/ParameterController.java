package com.rw.directories.controllers;

import com.rw.directories.dto.Parameter;
import com.rw.directories.services.ParameterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@Api(value="parameters", description="Сервис получение данных из справочника параметров СППД", tags = "Справочник параметров СППД", basePath="/directories")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ParameterController {
    @Autowired
    ParameterService parameterService;

    @RequestMapping(path="/${service.version}/directories/parameters", method = RequestMethod.GET)
    @ApiOperation(value = "Получение справочника параметров СППД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    List<Parameter> getParameters(@RequestHeader(name="IF-NONE-MATCH", required = false) @ApiParam(name="IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return parameterService.getParameters();
    }

    @RequestMapping(path="/${service.version}/directories/parameter/{code}", method = RequestMethod.GET)
    @ApiOperation(value = "Параметр СППД по коду")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    Parameter getParameterByCode(@PathVariable("code") @ApiParam(value="Код параметра") String code, @RequestHeader(name="IF-NONE-MATCH", required = false) @ApiParam(name="IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        return parameterService.getParameter(code);
    }


    @ExceptionHandler(ConnectException.class)
    protected com.rw.numbered.orders.dto.ErrorMessage handleConnectException(ConnectException e) {
        return new com.rw.numbered.orders.dto.ErrorMessage("CONNECT_ERROR", e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected com.rw.numbered.orders.dto.ErrorMessage handleDataAccessException(EmptyResultDataAccessException e) {
        return new com.rw.numbered.orders.dto.ErrorMessage("EMPTY_RESULT", e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    protected com.rw.numbered.orders.dto.ErrorMessage handleSQLException(SQLException e) {
        return new com.rw.numbered.orders.dto.ErrorMessage("SQL_ERROR", e.getMessage());
    }

}







