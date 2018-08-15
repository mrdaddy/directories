package com.rw.directories.services;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ParameterService {
    @Autowired
    private ParameterDao parameterDao;

    public List<Parameter> getParameters() {
        List<Parameter> parameters = parameterDao.getParameters();
        return parameters;
    }

    public Parameter getParameter(@NotNull String code) {
        return parameterDao.getParameterByCode(code);
    }

}
