package com.rw.directories.controllers;

import com.rw.directories.dto.Parameter;
import com.rw.directories.services.ParameterService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        testPars.add(new Parameter(Parameter.CATEGORY.T,"TEST_CODE","Test value"));
        testPars.add(new Parameter(Parameter.CATEGORY.T,"TEST_CODE2","Test value 2"));
        testPars.add(new Parameter(Parameter.CATEGORY.M,"TEST_CODE3","Test value 3"));
    }

    @Test
    public void getParameters() {
        when(parameterService.getParameters()).thenReturn(testPars);
        testPars = parameterController.getParameters("");
        assertThat(testPars.size(), is(3));
        verify(parameterService).getParameters();
    }

    @Test
    @SneakyThrows
    public void getParameterByCode() {
        when(parameterService.getParameter("TEST_CODE")).thenReturn(testPars.get(0));
        Parameter parameter = parameterController.getParameterByCode("TEST_CODE","");
        assertThat(parameter.getCode(), is("TEST_CODE"));
        verify(parameterService).getParameter(("TEST_CODE"));
    }
}