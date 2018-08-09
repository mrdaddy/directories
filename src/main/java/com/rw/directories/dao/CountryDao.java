package com.rw.directories.dao;

import com.rw.directories.dto.Country;
import com.rw.directories.dto.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class CountryDao {
    @Getter
    @Setter
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Country> getCountries() {
        List<Country> countries = jdbcTemplate.query(
                SQLQueries.COUNTRIES_INFO, new RowMapper<Country>() {
                    public Country mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return getCountry(rs);
                    }
                });
        return countries;
    }

    @SneakyThrows
    private Country getCountry(ResultSet rs)  {
        Country country = new Country();
       /* country.setCategory(rs.getString("TYPE"));
        parameter.setCode(rs.getString("CODE").trim());
        parameter.setValue(rs.getString("VALUE"));*/
        return country;
    }


}
