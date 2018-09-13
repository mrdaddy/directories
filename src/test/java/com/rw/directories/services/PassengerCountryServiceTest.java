package com.rw.directories.services;


import com.rw.directories.dao.PassengerCountryDao;
import com.rw.directories.dto.PassengerCountry;
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
public class PassengerCountryServiceTest {

    @Mock
    private PassengerCountryDao passengerCountryDao;

    @InjectMocks
    private PassengerCountryService passengerCountryService;
    private List<PassengerCountry> passengerCountriesTrue, passengerCountriesFake;
    private String lang;


    @Before
    public void setUp() {

        lang = new String("ru");
        passengerCountriesTrue = new ArrayList<>();
        passengerCountriesTrue.add(new PassengerCountry("NAME", "CODE"));
        passengerCountriesTrue.add(new PassengerCountry("test_2", "CODE_2"));

    }


    @Test
    public void getPassengerCountries() {

        when(passengerCountryDao.getPassengerCountries(lang)).thenReturn(passengerCountriesTrue);
        passengerCountriesFake = passengerCountryService.getPassengerCountries(lang);
        assertTrue(passengerCountriesFake.equals(passengerCountriesTrue));

    }

}