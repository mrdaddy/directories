package com.rw.directories.controllers;

import com.rw.directories.dto.EripFacilities;
import com.rw.directories.services.EripFacilitiesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "erip-facilities", description = "Платежные инструменты системы “Расчет“", tags = "Справочник платежных инструментов системы “Расчет“", basePath = "/erip-facilities")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EripFacilitiesController {

    @Autowired
    EripFacilitiesService eripFacilitiesService;

    @RequestMapping(path = "/${service.version}/directories/erip-facilities", method = RequestMethod.GET)
    @ApiOperation(value = "Получение справрочника платежных инструментов системы “Расчет“")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    List<EripFacilities> getEripFacilities(@RequestHeader(name = "IF-NONE-MATCH", required = false) @ApiParam(name = "IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) throws EmptyResultDataAccessException {
        return eripFacilitiesService.getEripFacilities();
    }
}
