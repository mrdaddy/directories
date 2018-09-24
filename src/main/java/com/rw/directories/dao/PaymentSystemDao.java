package com.rw.directories.dao;

import com.rw.directories.DirectoryFactory;
import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rw.directories.utils.DBUtils.formatQueryWithParams;

@Transactional
@Repository
public class PaymentSystemDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<PaymentSystem> getPaymentSystem(String language) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("IS_ACCESSIBLE", "1");
        List<PaymentSystem> systems = jdbcTemplate.query(
                formatQueryWithParams(SQLQueries.PAYMENT_SYSTEM_INFO, language), params, (rs, rowNum) -> getPaymentSystem(rs));
        return systems;
    }

    private PaymentSystem getPaymentSystem(ResultSet rs) throws SQLException {
        PaymentSystem system = DirectoryFactory.getDirectory(PaymentSystem.class, rs);
        system.setType(PaymentSystem.PAYMENT_SYSTEM.valueOf(rs.getString("PAYMENT_SYSTEM_TYPE")));
        system.setName(rs.getString("PAYMENT_SYSTEM_NAME"));
        system.setShortName(rs.getString("PAYMENT_SYSTEM_SN"));
        system.setDefault(DBUtils.toBoolean(rs.getInt("IS_DEFAULT")));
        system.setPaymentTime(rs.getInt("PAYMENT_TIME"));
        system.setEticketCode(rs.getString("ETICKET_CODE"));
        system.setEticketName(rs.getString("ETICKET_NAME"));
        system.setPaymentSuccessUrl(rs.getString("ETICKET_URL"));
        system.setPaymentErrorUrl(rs.getString("ETICKET_CANCEL_URL"));
        String addParams = rs.getString("ADDITIONAL_PARAMS");
        if(!StringUtils.isEmpty(addParams) && addParams.contains("test=1")) {

        }
        system.setTicketReturnEnabled(DBUtils.toBoolean(rs.getInt("IS_TICKET_RETURN_ENABLE")));
        system.setOnlineReturnEnabled(DBUtils.toBoolean(rs.getInt("IS_ONLINE_RETURN_ENABLE")));

        return system;
    }
}
