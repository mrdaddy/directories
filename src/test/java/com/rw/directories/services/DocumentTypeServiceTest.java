package com.rw.directories.services;


import com.rw.directories.dao.DocumentTypeDao;
import com.rw.directories.dto.DocumentType;
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
public class DocumentTypeServiceTest {

    @Mock
    private DocumentTypeDao documentTypeDao;

    @InjectMocks
    private DocumentTypeService documentTypeService;
    private List<DocumentType> documentTypesTrue, documentTypesFake;
    private String lang;


    @Before
    public void setUp() {

        lang = new String("ru");
        documentTypesTrue = new ArrayList<>();
        documentTypesTrue.add(new DocumentType("FH","NAME","STATUS",1,true,"CODE"));
        documentTypesTrue.add(new DocumentType("HF","NAME_2","STATUS_2",2,false,"CODE_2"));

    }


    @Test
    public void getDocumentTypes() {

        when(documentTypeDao.getDocumentTypes(lang)).thenReturn(documentTypesTrue);
        documentTypesFake = documentTypeService.getDocumentTypes(lang);
        assertTrue(documentTypesFake.equals(documentTypesTrue));

    }

}