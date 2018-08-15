package com.rw.directories.dao;

import com.rw.directories.DirectoryFactory;
import com.rw.directories.dto.PassengerCountry;
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
public class PassengerCountryDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<PassengerCountry> getPassengerCountries(String language) {
        List<PassengerCountry> countries = jdbcTemplate.query(
                formatQueryWithParams(SQLQueries.PASSENGER_COUNTRIES_INFO, language), (rs, rowNum) -> getPassengerCountry(rs));
        return countries;
    }

    private PassengerCountry getPassengerCountry(ResultSet rs) throws SQLException {
        PassengerCountry country = DirectoryFactory.getDirectory(PassengerCountry.class, rs);
        country.setIsoCode(rs.getString("ISO_CODE").trim());
        country.setName(rs.getString("NAME"));
        return country;
    }
}
