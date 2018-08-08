package com.rw.directories.services;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dto.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class ParameterService {
    @Getter @Setter
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
