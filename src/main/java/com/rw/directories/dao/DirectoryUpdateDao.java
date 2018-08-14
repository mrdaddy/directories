package com.rw.directories.dao;

import com.rw.directories.dto.Country;
import com.rw.directories.dto.DirectoryUpdate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.rw.directories.utils.DBUtils.formatQueryWithParams;

public class DirectoryUpdateDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @SneakyThrows
    public List<DirectoryUpdate> getDirectoryUpdates() {
        List<DirectoryUpdate>  directoryUpdates = jdbcTemplate.query(
                formatQueryWithParams(SQLQueries.COUNTRIES_INFO), (rs, rowNum) -> {
                    DirectoryUpdate directoryUpdate = new DirectoryUpdate();
                    directoryUpdate.setDirectory("");
                    return directoryUpdate;
                });
        return directoryUpdates;
    }

}
