package com.rw.directories.services;



import com.rw.directories.dao.CountryDao;
import com.rw.directories.dto.Country;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @Mock
    private CountryDao countryDao;

    @InjectMocks
    private CountryService countryService;
    private List<Country> countriesTrue, countriesFake;
    private String lang;

    @Before
    public void setUp() {
        lang = "ru";
        countriesTrue = new ArrayList<>();
        countriesTrue.add(new Country("1","NAME",true,5,13));
        countriesTrue.add(new Country("2","NAME_2",true,4,9));
    }


    @Test
    public void getCountries(){

        when(countryDao.getCountries(lang)).thenReturn(countriesTrue);
        countriesFake = countryService.getCountries(lang);
        assertTrue(countriesFake.equals(countriesTrue));

    }

}