package com.rw.directories.services;

import com.rw.directories.dao.PaymentSystemDao;
import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.utils.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class PaymentSystemService {

    @Autowired
    private PaymentSystemDao paymentSystemDao;

    public List<PaymentSystem> getPaymentSystem(@NotNull String lang) {
        return paymentSystemDao.getPaymentSystem(lang);
    }

}
