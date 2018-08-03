package com.rw.directories.services;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterService {
    @Autowired
    private ParameterDao parameterDao;

    public List<Parameter> getParameters() {
        List<Parameter> parameters = parameterDao.getParameters();
        return parameters;
    }

    public Parameter getParameter(String code) {
        return parameterDao.getParameterByCode(code);
    }

}
