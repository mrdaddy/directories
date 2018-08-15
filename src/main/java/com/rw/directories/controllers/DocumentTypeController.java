package com.rw.directories.controllers;

import com.rw.directories.dto.DocumentType;
import com.rw.directories.services.DocumentTypeService;
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
@Api(value="doc-types", description="Сервис получение данных из справочника типов документов, удостоверяющих личность пассажира", tags = "Список типов документов, удостоверяющих личность пассажира", basePath="/doc-types")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentTypeController {
    @Autowired
    DocumentTypeService documentTypeService;

    @RequestMapping(path="/${service.version}/directories/doc-types", method = RequestMethod.GET)
    @ApiOperation(value = "Список типов документов")
    List<DocumentType> getDocumentTypes(@RequestParam String lang) {
        return documentTypeService.getDocumentTypes(lang);
    }
}
