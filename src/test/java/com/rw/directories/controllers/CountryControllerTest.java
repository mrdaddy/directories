package com.rw.directories.controllers;

import com.rw.directories.dto.Country;
import com.rw.directories.services.CountryService;
import com.rw.directories.utils.LanguageUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {


    @Mock
    private CountryService countryService;

    @InjectMocks
    private  CountryController countryController;
    private  List<Country> countriesTrue;
    private  List<Country> countriesFake;

    private  String lang,mock;


    @Before
    public void setUp() {
          lang = "ru";
          mock = "test";
          countriesTrue = new ArrayList<>();
          countriesTrue.add(new Country("1","NAME",true,5,16));
          countriesTrue.add(new Country("2","NAME_2",true,4,14));

    }

    @Test
    public void getCountries(){

        when(countryService.getCountries(lang)).thenReturn(countriesTrue);
        countriesFake = countryController.getCountries(LanguageUtils.SUPPORTED_LANGUAGES.valueOf(lang),mock);
        assertTrue(countriesFake.equals(countriesTrue));
    }

}
