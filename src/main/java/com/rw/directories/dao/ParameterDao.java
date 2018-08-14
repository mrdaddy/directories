package com.rw.directories.dao;

import com.rw.directories.ParameterConst;
import com.rw.directories.dto.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Transactional
@Repository
public class ParameterDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Parameter> getParameters() {
        List<Parameter> params = jdbcTemplate.query(
                SQLQueries.PARAMS_INFO, (rs, rowNum) -> getParameter(rs));
        return params;
    }

    public Parameter getParameterByCode(String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("CODE", code);
        Parameter param = jdbcTemplate.queryForObject(
                SQLQueries.PARAM_INFO, params, (rs, rowNum) -> getParameter(rs));
        return param;
    }

    @SneakyThrows
    private Parameter getParameter(ResultSet rs)  {
        Parameter parameter = new Parameter();
        parameter.setId(rs.getInt("ID"));
        parameter.setCategory(rs.getString("TYPE"));
        parameter.setCode(rs.getString("CODE").trim());
        parameter.setValue(rs.getString("VALUE"));
        parameter.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        return parameter;
    }


}
