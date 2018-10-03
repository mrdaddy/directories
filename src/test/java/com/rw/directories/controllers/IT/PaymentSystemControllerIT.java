package com.rw.directories.controllers.IT;


import com.rw.directories.dto.PaymentSystem;
import com.rw.directories.utils.DBUtils;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentSystemControllerIT {

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;

    private MockMvc mockMvc;
    public List<PaymentSystem> systemsTrue, systemsFake;

    private static final String PATH_PAYMENT_SYSTEMS_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForPaymentSystemIT";
    private static final String PATH_PAYMENT_SYSTEMS_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForPaymentSystemIT";

    @Before
    public void setUp() throws IOException {

        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
        systemsFake = new ArrayList<>();
        systemsTrue = new ArrayList<>();



        try {
            FileInputStream fstream = new FileInputStream(PATH_PAYMENT_SYSTEMS_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                PaymentSystem system = new PaymentSystem();
                system.setName(jsonArray.getJSONObject(i).getString("name"));
                system.setDefault(jsonArray.getJSONObject(i).getBoolean("default"));
                system.setType(PaymentSystem.PAYMENT_SYSTEM.valueOf(jsonArray.getJSONObject(i).getString( "type")));
                system.setShortName(jsonArray.getJSONObject(i).getString("shortName"));
                system.setPaymentTime(jsonArray.getJSONObject(i).getInt("paymentTime"));
                system.setShortName(jsonArray.getJSONObject(i).getString("shortName"));
                system.setEticketCode(jsonArray.getJSONObject(i).getString("eticketCode"));
                system.setEticketName(jsonArray.getJSONObject(i).getString("eticketName"));
                system.setPaymentSuccessUrl(jsonArray.getJSONObject(i).getString("paymentSuccessUrl"));
                system.setPaymentErrorUrl(jsonArray.getJSONObject(i).getString("paymentErrorUrl"));
                system.setTicketReturnEnabled(jsonArray.getJSONObject(i).getBoolean("ticketReturnEnabled"));
                system.setOnlineReturnEnabled(jsonArray.getJSONObject(i).getBoolean("onlineReturnEnabled"));
                system.setTest(jsonArray.getJSONObject(i).getBoolean("test"));

                systemsTrue.add(system);
            }
        } catch (IOException e) {
            System.out.println("Input PAYMENT_SYSTEMS data error. Initialization failed");
        }

        List<String> lines = Files.readAllLines(Paths.get(PATH_PAYMENT_SYSTEMS_SQL));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }

        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PAYMENT_SYSTEMS");
        }catch (BadSqlGrammarException ex){}
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable = new String();
        for (PaymentSystem system : systemsTrue) {
            sqlInsertDataInTable = "INSERT INTO ETICKET.PAYMENT_SYSTEMS(PAYMENT_SYSTEM_TYPE,PAYMENT_SYSTEM_NAME_EN,PAYMENT_SYSTEM_NAME_RU, PAYMENT_SYSTEM_SN_EN, PAYMENT_SYSTEM_SN_RU,IS_DEFAULT," +
                    "PAYMENT_TIME,ETICKET_CODE,ETICKET_NAME,ETICKET_URL,ETICKET_CANCEL_URL,IS_TICKET_RETURN_ENABLE,IS_ONLINE_RETURN_ENABLE) VALUES ( :type , :name , :name, :shortName , " +
                    ":shortName , :isDefault , :time, :systemCode, :systemName, :systemURL, :systemCancelURL, :ticketReturn, :ticketReturnOnline)";

            Map namedParameters = new HashMap();
            namedParameters.put("type", system.getType().toString());
            namedParameters.put("name", system.getName());
            namedParameters.put("shortName", system.getShortName());
            namedParameters.put("isDefault", DBUtils.toString(system.isDefault()));
            //namedParameters.put("isAccessebleRU",DBUtils.toString(system.isAccessibleRU()));
            //namedParameters.put("isAccessebleEN", DBUtils.toString(system.isAccessibleEN()));
            //namedParameters.put("url",  system.getUrl());
            namedParameters.put("time", system.getPaymentTime());
            namedParameters.put("systemCode", system.getEticketCode());
            namedParameters.put("systemName", system.getEticketName());
            namedParameters.put("systemURL", system.getPaymentSuccessUrl());
            namedParameters.put("systemCancelURL", system.getPaymentErrorUrl());
            //namedParameters.put("additional", system.getAdditionalParams());
            //namedParameters.put("payAgent", system.getPayingAgent());
            namedParameters.put("ticketReturn", DBUtils.toString(system.isTicketReturnEnabled()));
            namedParameters.put("ticketReturnOnline", DBUtils.toString(system.isOnlineReturnEnabled()));

            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
        }

    }

    @Test
    public void testGetPaymentSystemList() {

        ResponseEntity<PaymentSystem[]> systems = restTemplate.getForEntity(
                "/" + version + "/directories/ps?lang=en&inm=test", PaymentSystem[].class);
        systemsFake = Arrays.asList(systems.getBody());
        assertEquals(systemsTrue, systemsFake);

        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PAYMENT_SYSTEMS");
    }
}


