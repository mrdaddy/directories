package com.rw.directories.controllers;

import com.rw.directories.dto.DirectoryUpdate;
import com.rw.directories.dto.PassengerCountry;
import com.rw.directories.services.DirectoryUpdateService;
import com.rw.directories.services.PassengerCountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PassengerCountryControllerTest {

    @Mock
    private PassengerCountryService passengerCountryService;

    @InjectMocks
    private PassengerCountryController passengerCountryController;
    private List<PassengerCountry> passengerCountriesTrue;
    private List<PassengerCountry> passengerCountriesFake;
    private String lang,mock;

    @Before
    public void setUp(){

        lang = new String("ru");
        mock = new String("ru");
        passengerCountriesTrue = new ArrayList<>();
        passengerCountriesTrue.add(new PassengerCountry("NAME", "CODE"));
        passengerCountriesTrue.add(new PassengerCountry("test_2", "CODE_2"));

    }

    @Test
    public void getPassengerCountries(){

        when(passengerCountryService.getPassengerCountries(lang)).thenReturn(passengerCountriesTrue);
        passengerCountriesFake = passengerCountryController.getPassengerCountries(lang,mock);
        assertTrue(passengerCountriesTrue.equals(passengerCountriesFake));

    }
}
