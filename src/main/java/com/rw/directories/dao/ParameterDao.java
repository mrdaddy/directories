package com.rw.directories.dao;

import com.rw.directories.DirectoryFactory;
import com.rw.directories.dto.Directory;
import com.rw.directories.dto.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class ParameterDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Parameter> getParameters(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IS_API_PROPAGATED", "1");
        List<Parameter> parameters = jdbcTemplate.query(
                SQLQueries.PARAMS_INFO, params, (rs, rowNum) -> getParameter(rs));
        return parameters;
    }

    public Parameter getParameterByCode(String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("CODE", code);
        params.put("IS_API_PROPAGATED", "1");
        Parameter parameter = jdbcTemplate.queryForObject(
                SQLQueries.PARAM_INFO, params, (rs, rowNum) -> getParameter(rs));
        return parameter;
    }

    private Parameter getParameter(ResultSet rs) throws SQLException {

        Parameter parameter = DirectoryFactory.getDirectory(Parameter.class, rs);

        parameter.setCategory(Parameter.CATEGORY.valueOf(rs.getString("TYPE")));
        parameter.setCode(rs.getString("CODE").trim());
        parameter.setValue(rs.getString("VALUE"));
        return parameter;
    }


}
