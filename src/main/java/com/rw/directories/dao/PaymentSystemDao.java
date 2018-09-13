package com.rw.directories.dao;

import com.rw.directories.BooleanTransformer;
import com.rw.directories.DirectoryFactory;
import com.rw.directories.dto.PaymentSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.rw.directories.utils.DBUtils.formatQueryWithParams;

@Transactional
@Repository
public class PaymentSystemDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<PaymentSystem> getPaymentSystem(String language) {
        List<PaymentSystem> systems = jdbcTemplate.query(
                formatQueryWithParams(SQLQueries.PAYMENT_SYSTEM_INFO, language), (rs, rowNum) -> getPaymentSystem(rs));
        return systems;
    }

    private PaymentSystem getPaymentSystem(ResultSet rs) throws SQLException {
        PaymentSystem system = DirectoryFactory.getDirectory(PaymentSystem.class, rs);
        system.setType(rs.getString("PAYMENT_SYSTEM_TYPE"));
        system.setName(rs.getString("PAYMENT_SYSTEM_NAME_EN"));
        system.setShortName(rs.getString("PAYMENT_SYSTEM_SN_EN"));
        system.setDefaultFromString(rs.getString("IS_DEFAULT"));
        system.setAccessibleENFromString(rs.getString("IS_ACCESSIBLE_EN"));
        system.setAccessibleRUFromString(rs.getString("IS_ACCESSIBLE_RU"));
        system.setUrl(rs.getString("PAYMENT_SYSTEM_URL"));
        system.setTime(rs.getInt("PAYMENT_TIME"));
        system.setSystemPayCode(rs.getString("ETICKET_CODE"));
        system.setSystemPayName(rs.getString("ETICKET_NAME"));
        system.setSystemPayUrl(rs.getString("ETICKET_URL"));
        system.setSystemPayCancelUrl(rs.getString("ETICKET_CANCEL_URL"));
        system.setAdditionalParams(rs.getString("ADDITIONAL_PARAMS"));
        system.setPayingAgent(rs.getString("PAYING_AGENT"));
        system.setTicketReturnUnableFromString(rs.getString("IS_TICKET_RETURN_ENABLE"));
        system.setOnlineReturnUnableFromString(rs.getString("IS_ONLINE_RETURN_ENABLE"));

        return system;
    }
}
