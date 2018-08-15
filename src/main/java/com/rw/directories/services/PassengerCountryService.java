package com.rw.directories.services;

import com.rw.directories.dao.PassengerCountryDao;
import com.rw.directories.dto.PassengerCountry;
import com.rw.directories.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class PassengerCountryService {
    @Autowired
    private PassengerCountryDao passengerCountryDao;

    public List<PassengerCountry> getPassengerCountries(@NotNull String lang) {
        List<PassengerCountry> countries = passengerCountryDao.getPassengerCountries(LanguageUtils.convertToSupportedLang(lang));
        return countries;
    }
}
