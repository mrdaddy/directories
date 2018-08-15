package com.rw.directories.services;

import com.rw.directories.dao.DocumentTypeDao;
import com.rw.directories.dto.DocumentType;
import com.rw.directories.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class DocumentTypeService {
    @Autowired
    private DocumentTypeDao documentTypeDao;

    public List<DocumentType> getDocumentTypes(@NotNull String lang) {
        List<DocumentType> documentTypes = documentTypeDao.getDocumentTypes(LanguageUtils.convertToSupportedLang(lang));
        return documentTypes;
    }

}
