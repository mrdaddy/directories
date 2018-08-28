package com.rw.directories.dao;

import com.rw.directories.DirectoryFactory;
import com.rw.directories.dto.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.rw.directories.utils.DBUtils.formatQueryWithParams;
import static com.rw.directories.utils.DBUtils.toBoolean;

@Transactional
@Repository
public class DocumentTypeDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<DocumentType> getDocumentTypes(String language) {
        List<DocumentType> countries = jdbcTemplate.query(
                formatQueryWithParams(SQLQueries.DOCUMENT_TYPES_INFO, language), (rs, rowNum) -> getDocumentType(rs));
        return countries;
    }

    private DocumentType getDocumentType(ResultSet rs) throws SQLException {
        DocumentType documentType = DirectoryFactory.getDirectory(DocumentType.class, rs);
        documentType.setCode(rs.getString("CODE").trim());
        documentType.setName(rs.getString("NAME"));
        documentType.setStatus(rs.getString("STATUS"));
        documentType.setExpressCode(rs.getString("EXPRESS_CODE"));
        documentType.setUsedForGlobalPrice(toBoolean(rs.getInt("IS_GP_USED")));
        documentType.setUseForET(rs.getInt("USE_FOR_ET"));
        return documentType;
    }
}
