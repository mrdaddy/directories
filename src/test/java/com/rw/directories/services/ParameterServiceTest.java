package com.rw.directories.services;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParameterServiceTest {

    @Mock
    private ParameterDao parameterDao;

    @InjectMocks
    private ParameterService parameterService;
    private List<Parameter> parametersTrue, parametersFake;

    @Before
    public void setUp() {
        parametersTrue = new ArrayList<>();
        parametersFake = new ArrayList<>();
        parametersTrue.add(new Parameter("T","TEST_CODE","Test value"));
        parametersTrue.add(new Parameter("T","TEST_CODE2","Test value 2"));
        parametersTrue.add(new Parameter("M","TEST_CODE3","Test value 3"));
    }


        @Test
        public void getParameters() {
            when(parameterDao.getParameters()).thenReturn(parametersTrue);
            parametersFake = parameterService.getParameters();
            assertTrue(parametersFake.equals(parametersTrue));
        }


        @Test
        public void getParameter() {
            when(parameterDao.getParameterByCode(parametersTrue.get(0).getCode())).thenReturn(parametersTrue.get(0));
            parametersFake.add(parameterService.getParameter(parametersTrue.get(0).getCode()));
            assertTrue(parametersFake.get(0).equals(parametersTrue.get(0)));
    }

}
