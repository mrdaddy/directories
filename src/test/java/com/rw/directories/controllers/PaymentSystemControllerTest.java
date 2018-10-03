package com.rw.directories.controllers;

import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.services.PaymentSystemService;
import com.rw.directories.utils.LanguageUtils;
import org.junit.Before;
import org.junit.Ignore;
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
            systemsTrue.add(new PaymentSystem(PaymentSystem.PAYMENT_SYSTEM.valueOf("ERIP"), "NAME", "SHORTNAME",  true,
                    15, "1001011", "payName", "payURL", "payCancelURL",  true, false, true));
            systemsTrue.add(new PaymentSystem(PaymentSystem.PAYMENT_SYSTEM.valueOf("ERIP"), "ERIP", "rasschet", true,
                    15, "1938403", "erip", "payURL", "payCancelURL", true, false, true));

        }

        @Test
        public void getPaymentSystems() {
            when(paymentSystemService.getPaymentSystem("en")).thenReturn(systemsTrue);
            systemsFake = paymentSystemController.getPaymentSystem(LanguageUtils.SUPPORTED_LANGUAGES.valueOf("en"),"test");
            assertTrue(systemsFake.equals(systemsTrue));
        }

    }
