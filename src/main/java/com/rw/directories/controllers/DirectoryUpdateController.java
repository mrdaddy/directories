package com.rw.directories.controllers;

import com.rw.directories.dto.DirectoryUpdate;
import com.rw.directories.services.DirectoryUpdateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value="dir-updates", description="Сервис получение данных из справочника стран, в которые могут продаваться проездные документы", tags = "Список наименований справочников и даты их последнего обновления", basePath="/dir-updates")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectoryUpdateController {
    @Autowired
    DirectoryUpdateService directoryUpdateService;

    @RequestMapping(path="/${service.version}/directories/dir-updates", method = RequestMethod.GET)
    @ApiOperation(value = "Получение списка всех справочников с датой и временем последнего обновления")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "ETag", response = String.class, description = "Хеш для кэширования")}),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    List<DirectoryUpdate> getDirectoryUpdates(@RequestHeader(name="IF-NONE-MATCH", required = false) @ApiParam(name="IF-NONE-MATCH", value = "ETag из предыдущего закэшированного запроса") String inm) {
        List<DirectoryUpdate> directoryUpdates = directoryUpdateService.getDirectoryUpdates();
        return directoryUpdates;
    }


}
