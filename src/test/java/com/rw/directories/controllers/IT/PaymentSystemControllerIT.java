package com.rw.directories.controllers.IT;


import com.rw.directories.BooleanTransformer;
import com.rw.directories.dto.PaymentSystem;
import org.junit.Before;
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

    private BooleanTransformer booleanTransformer;
    private MockMvc mockMvc;
    public List<PaymentSystem> systemsTrue, systemsFake;

    private static final String PATH_PAYMENT_SYSTEMS_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForPaymentSystemIT";
    private static final String PATH_PAYMENT_SYSTEMS_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForPaymentSystemIT";

    @Before
    public void setUp() throws IOException {

        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
        booleanTransformer = new BooleanTransformer();
        systemsFake = new ArrayList<>();
        systemsTrue = new ArrayList<>();

        try {
            FileInputStream fstream = new FileInputStream(PATH_PAYMENT_SYSTEMS_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreatePaySystem = strLine.split(",");
                systemsTrue.add(new PaymentSystem(argumentsForCreatePaySystem[0].trim(),
                        argumentsForCreatePaySystem[1],argumentsForCreatePaySystem[2],
                        booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[3]), argumentsForCreatePaySystem[4].trim(),Integer.parseInt(argumentsForCreatePaySystem[5]),
                        argumentsForCreatePaySystem[6].trim(), argumentsForCreatePaySystem[7].trim(), argumentsForCreatePaySystem[8].trim(), argumentsForCreatePaySystem[9].trim(),
                        argumentsForCreatePaySystem[10].trim(),  booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[11]),
                        booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[12]), argumentsForCreatePaySystem[13].trim(),
                        booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[14]),  booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[15])));
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
        int id = 1;
        for (PaymentSystem system : systemsTrue) {
            sqlInsertDataInTable = "INSERT INTO ETICKET.PAYMENT_SYSTEMS VALUES (:id, :type , :name , :name, :shortName , :shortName , :isDefault , :isAccessebleRU, " +
                    ":isAccessebleEN, :url, :time, :systemCode, :systemName, :systemURL, :systemCancelURL, :additional, :payAgent, :ticketReturn, :ticketReturnOnline)";

            Map namedParameters = new HashMap();
            namedParameters.put("id", id);
            namedParameters.put("type", system.getType());
            namedParameters.put("name", system.getName());
            namedParameters.put("shortName", system.getShortName());
            namedParameters.put("isDefault", booleanTransformer.transformToChar(system.isDefault()));
            namedParameters.put("isAccessebleRU",booleanTransformer.transformToChar(system.isAccessibleRU()));
            namedParameters.put("isAccessebleEN", booleanTransformer.transformToChar(system.isAccessibleEN()));
            namedParameters.put("url",  system.getUrl());
            namedParameters.put("time", system.getTime());
            namedParameters.put("systemCode", system.getSystemPayCode());
            namedParameters.put("systemName", system.getSystemPayName());
            namedParameters.put("systemURL", system.getSystemPayUrl());
            namedParameters.put("systemCancelURL", system.getSystemPayCancelUrl());
            namedParameters.put("additional", system.getAdditionalParams());
            namedParameters.put("payAgent", system.getPayingAgent());
            namedParameters.put("ticketReturn", booleanTransformer.transformToChar(system.isTicketReturnUnable()));
            namedParameters.put("ticketReturnOnline", booleanTransformer.transformToChar(system.isOnlineReturnUnable()));

            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
            id ++;
        }

    }

    @Test
    public void testGetPaymentSystemList() {

        ResponseEntity<PaymentSystem[]> systems = restTemplate.getForEntity(
                "/" + version + "/directories/pay-systems?lang=en&inm=test", PaymentSystem[].class);
        systemsFake = Arrays.asList(systems.getBody());

        //assert failed because url not come up from server
        assertEquals(systemsTrue, systemsFake);

        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PAYMENT_SYSTEMS");
    }
}


