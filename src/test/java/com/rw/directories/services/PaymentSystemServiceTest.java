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
        systemsTrue.add(new PaymentSystem("TYPE", "NAME", "SN", true, "url",
                15, "1001011", "payName", "payURL", "payCancelURL", "test", true,
                false, "agent", true, false));
        systemsTrue.add(new PaymentSystem("ERIP", "rasschet", "erip", true, "url",
                15, "1938403", "erip", "payURL", "payCancelURL", "test", true,
                false, "agent", true, false));

    }


    @Test
    public void getParameters() {
        when(paymentSystemDao.getPaymentSystem("EN")).thenReturn(systemsTrue);
        systemsFake = paymentSystemService.getPaymentSystem("EN");
        assertTrue(systemsFake.equals(systemsTrue));
    }

}