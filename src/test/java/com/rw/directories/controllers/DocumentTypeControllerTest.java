package com.rw.directories.controllers;


import com.rw.directories.dto.DocumentType;
import com.rw.directories.services.DocumentTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class DocumentTypeControllerTest{

    @Mock
    DocumentTypeService documentTypeService;

    @InjectMocks
    private DocumentTypeController documentTypeController;
    private List<DocumentType> documentTypesTrue;
    private List<DocumentType> documentTypesFake;
    private String lang,mock;

    @Before
    public void setUp(){

        lang = "ru";
        mock = "test";
        documentTypesTrue = new ArrayList<>();
        documentTypesTrue.add(new DocumentType("FH","NAME","STATUS",1,true,"CODE"));
        documentTypesTrue.add(new DocumentType("HF","NAME_2","STATUS_2",2,false,"CODE_2"));

    }

    @Test
    public void getDocumentTypes(){

        when(documentTypeService.getDocumentTypes(lang)).thenReturn(documentTypesTrue);
        documentTypesFake = documentTypeController.getDocumentTypes(lang,mock);
        assertTrue(documentTypesTrue.equals(documentTypesFake));

    }
}

