package com.rw.directories.services;

import com.rw.directories.dao.CountryDao;
import com.rw.directories.dto.Country;
import com.rw.directories.dto.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CountryService {
    @Getter
    @Setter
    @Autowired
    private CountryDao countryDao;

    public List<Country> getCountries(@NotNull String lang) {
        List<Country> countries = countryDao.getCountries(lang);
        return countries;
    }

}
