package com.rw.directories.services;

import com.rw.directories.dao.ParameterDao;
import com.rw.directories.dao.PaymentSystemDao;
import com.rw.directories.dto.Parameter;
import com.rw.directories.dto.PaymentSystem;
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
public class PaymentSystemServiceTest {

    @Mock
    private PaymentSystemDao paymentSystemDao;

    @InjectMocks
    private PaymentSystemService paymentSystemService;
    private List<PaymentSystem> systemsFake, systemsTrue;

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
    public void getParameters() {
        when(paymentSystemDao.getPaymentSystem("EN")).thenReturn(systemsTrue);
        systemsFake = paymentSystemService.getPaymentSystem("EN");
        assertTrue(systemsFake.equals(systemsTrue));
    }

}