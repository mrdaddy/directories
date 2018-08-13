package com.rw.directories.dao;

import com.rw.directories.dto.Country;
import com.rw.directories.utils.DBUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.rw.directories.utils.DBUtils.*;

@Transactional
@Repository
public class CountryDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @SneakyThrows
        public List<Country> getCountries(String language) {
        List<Country> countries = jdbcTemplate.query(
                formatQueryWithParams(SQLQueries.COUNTRIES_INFO, language), new RowMapper<Country>() {
                    public Country mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return getCountry(rs);
                    }
                });
        return countries;
    }

    private Country getCountry(ResultSet rs) throws SQLException {
        Country country = new Country();
        country.setId(rs.getInt("ID"));
        country.setCode(rs.getString("CODE").trim());
        country.setTicketSellingAllowed(toBoolean(rs.getInt("TICKET_SELLING_ALLOWED")));
        country.setStationSelectPriority(rs.getInt("STATION_SELECT_PRIORITY"));
        country.setName(rs.getString("NAME"));
        country.setTimeZone(rs.getString("TIME_ZONE"));
        country.setDepartureMsg(rs.getString("DEPARTURE_MSG"));
        country.setArrivalMsg(rs.getString("ARRIVAL_MSG"));
        country.setGlobalPrice(toBoolean(rs.getInt("IS_GLOBAL_PRICE")));
        country.setFreeTicketAge(rs.getInt("FREE_TICKET_AGE"));
        country.setChildrenTicketAge(rs.getInt("CHILDREN_TICKET_AGE"));

        country.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        return country;
    }


}
