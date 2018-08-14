package com.rw.directories.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="countries", description="Сервис получение данных из справочника стран, в которые могут продаваться проездные документы", tags = "Справочник стран, в которые могут продаваться проездные документы", basePath="/countries")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectoryUpdateController {
}
