package com.rw.directories.dao;

import com.rw.directories.dto.DirectoryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.rw.directories.utils.DBUtils.formatQueryWithParams;

@Transactional
@Repository
public class DirectoryUpdateDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<DirectoryUpdate> getDirectoryUpdates() {
        List<DirectoryUpdate>  directoryUpdates = jdbcTemplate.query(
                SQLQueries.DIRECTORY_UPDATES_INFO, (rs, rowNum) -> getDirectoryUpdate(rs));
        return directoryUpdates;
    }

    private DirectoryUpdate getDirectoryUpdate(ResultSet rs) throws SQLException {
        DirectoryUpdate directoryUpdate = new DirectoryUpdate();
        directoryUpdate.setDirectory(rs.getString("DIRECTORY"));
        directoryUpdate.setLastUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        return directoryUpdate;
    }


}
