package com.rw.directories.services;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Service
@Validated
public class ParameterService {
    @Autowired
    private ParameterDao parameterDao;

    public List<Parameter> getParameters() {
        List<Parameter> parameters = parameterDao.getParameters();
        return parameters;
    }

    public Parameter getParameter(@Valid @Size(min = 1, max = 32) @NotNull String code) {
        return parameterDao.getParameterByCode(code);
    }

}
