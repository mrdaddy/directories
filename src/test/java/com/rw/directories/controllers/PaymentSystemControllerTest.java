package com.rw.directories.controllers;

import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.services.PaymentSystemService;
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
    public class PaymentSystemControllerTest {
        @Mock
        private PaymentSystemService paymentSystemService;

        @InjectMocks
        private PaymentSystemController paymentSystemController;
        private List<PaymentSystem> systemsTrue, systemsFake;

        @Before
        public void setUp() {
            systemsFake = new ArrayList<>();
            systemsTrue = new ArrayList<>();
            systemsTrue.add(new PaymentSystem("TYPE","NAME","SN",true,"url",
                    15,"1001011","payName","payURL","payCancelURL","test",true,
                    false,"agent",true, false));
            systemsTrue.add(new PaymentSystem("ERIP", "rasschet", "erip", true, "url",
                    15, "1938403", "erip", "payURL", "payCancelURL", "test", true,
                    false, "agent", true, false));

        }

        @Test
        public void getPaymentSystems() {
            when(paymentSystemService.getPaymentSystem("EN")).thenReturn(systemsTrue);
            systemsFake = paymentSystemController.getPaymentSystem("EN","test");
            assertTrue(systemsFake.equals(systemsTrue));
        }

    }
