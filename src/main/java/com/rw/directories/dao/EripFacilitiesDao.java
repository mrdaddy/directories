package com.rw.directories.dao;

import com.rw.directories.DirectoryFactory;
import com.rw.directories.dto.EripFacilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class EripFacilitiesDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<EripFacilities> getEripFacilities() {
        List<EripFacilities> facilities = jdbcTemplate.query(
                SQLQueries.ERIP_FACILITIES_INFO, (rs, rowNum) -> getEripFacility(rs));
        return facilities;
    }

    private EripFacilities getEripFacility(ResultSet rs) throws SQLException {
        EripFacilities facility = DirectoryFactory.getDirectory(EripFacilities.class, rs);
        facility.setType(rs.getString("FACILITY_TYPE").trim());
        facility.setName(rs.getString("FACILITY_NAME"));
        facility.setUrl(rs.getString("FACILITY_URL"));
        return facility;
    }
}
