
package com.rw.directories.controllers;

import com.rw.directories.dto.EripFacilities;
import com.rw.directories.services.EripFacilitiesService;
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
public class EripFacilitiesControllerTest {

    @Mock
    private EripFacilitiesService eripFacilitiesService;

    @InjectMocks
    private EripFacilitiesController eripFacilitiesController;
    private List<EripFacilities> eripFacilitiesTrue, eripFacilitiesFake;

    @Before
    public void setUp() {

        eripFacilitiesTrue = new ArrayList<>();
        eripFacilitiesTrue.add(new EripFacilities("IB","Интернет-банкинг ОАО “Белагропромбанк“","https://www.ibank.belapb.by/"));
        eripFacilitiesTrue.add(new EripFacilities("EMS","WebMoney","http://www.wmtransfer.by/"));
        eripFacilitiesTrue.add(new EripFacilities("PS","WEBPAY","https://e-pay.by/"));

    }

    @Test
    public void getPassengerCountries() {

        when(eripFacilitiesService.getEripFacilities()).thenReturn(eripFacilitiesTrue);
        eripFacilitiesFake = eripFacilitiesController.getEripFacilities("test");
        assertTrue(eripFacilitiesFake.equals(eripFacilitiesTrue));

    }
}

