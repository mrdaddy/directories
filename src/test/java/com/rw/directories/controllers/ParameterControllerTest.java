package com.rw.directories.controllers;

import com.google.common.collect.ImmutableList;
import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import com.rw.directories.services.ParameterService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class ParameterControllerTest {
    @Mock
    private ParameterService parameterService;

    @InjectMocks
    private ParameterController parameterController;
    private List<Parameter> testPars;

    @Before
    public void setUp() {
        testPars = new ArrayList<>();
        testPars.add(new Parameter("T","TEST_CODE","Test value"));
        testPars.add(new Parameter("T","TEST_CODE2","Test value 2"));
        testPars.add(new Parameter("M","TEST_CODE3","Test value 3"));
    }

    @Test
    public void getParameters() {
        when(parameterService.getParameters()).thenReturn(testPars);
        testPars = parameterController.getParameters();
        assertThat(testPars.size(), is(3));
        verify(parameterService).getParameters();
    }

    @Test
    @SneakyThrows
    public void getParameterByCode() {
        when(parameterService.getParameter("TEST_CODE")).thenReturn(testPars.get(0));
        Parameter parameter = parameterController.getParameterByCode("TEST_CODE");
        assertThat(parameter.getCode(), is("TEST_CODE"));
        verify(parameterService).getParameter(("TEST_CODE"));
    }
}