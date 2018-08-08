package com.rw.directories.controllers;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import com.rw.directories.services.ParameterService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.core.Is.is;

public class ParameterControllerTest {
    private ParameterController parameterController;
    private ParameterService parameterService;
    private ParameterDao parameterDao;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() {
        initMocks(this);
        parameterController = new ParameterController();
        parameterDao = new ParameterDao();
        parameterService = new ParameterService();
        parameterDao.setJdbcTemplate(jdbcTemplate);
        parameterService.setParameterDao(parameterDao);
        parameterController.setParameterService(parameterService);
    }

    @Test
    public void getParameters() {
        List<Parameter> testPars = new ArrayList<>();
        testPars.add(new Parameter("T","TEST_CODE","Test value"));
        testPars.add(new Parameter("T","TEST_CODE2","Test value 2"));
        testPars.add(new Parameter("M","TEST_CODE3","Test value 3"));

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(testPars);

        testPars = parameterController.getParameters();
        assertThat(testPars.size(), is(3));

    }

    @Test
    @SneakyThrows
    public void getParameterByCode() {

      /*  when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("CODE")).thenReturn("TEST_CODE3");
        when(resultSet.getString("CATEGORY")).thenReturn("M");
        when(resultSet.getString("VALUE")).thenReturn("Test value 3");*/
        when(jdbcTemplate.queryForObject(anyString(), anyMap(), any(RowMapper.class))).thenReturn(new Parameter("M","TEST_CODE3","Test value 3"));

        Parameter parameter = parameterController.getParameterByCode("TEST_CODE3");
        assertThat(parameter.getCode(), is("TEST_CODE3"));
    }
}