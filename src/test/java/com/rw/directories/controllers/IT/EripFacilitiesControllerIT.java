package com.rw.directories.controllers.IT;


        import com.rw.directories.BooleanTransformer;
        import com.rw.directories.dto.EripFacilities;
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

        import static junit.framework.TestCase.*;
        import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EripFacilitiesControllerIT {

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;

    private BooleanTransformer booleanTransformer;
    private MockMvc mockMvc;
    public List<EripFacilities> facilitiesTrue, facilitiesFake;

    private static final String PATH_ERIP_FACILITIES_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForEripFacilitiesIT";
    private static final String PATH_ERIP_FACILITIES_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForEripFacilitiesIT";

    @Before
    public void setUp() throws IOException {

        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
        booleanTransformer = new BooleanTransformer();
        facilitiesFake = new ArrayList<>();
        facilitiesTrue = new ArrayList<>();

        try {
            FileInputStream fstream = new FileInputStream(PATH_ERIP_FACILITIES_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreateEripFacilities = strLine.split(",");
                facilitiesTrue.add(new EripFacilities(argumentsForCreateEripFacilities[0],
                        argumentsForCreateEripFacilities[1],argumentsForCreateEripFacilities[2]));}
        } catch (IOException e) {
            System.out.println("Input ERIP_FACILITIES data error. Initialization failed");
        }

        List<String> lines = Files.readAllLines(Paths.get(PATH_ERIP_FACILITIES_SQL));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }

        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.ERIP_PAYMENT_FACILITIES");
        }catch (BadSqlGrammarException ex){}
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable = new String();
        int id = 1;
        for (EripFacilities facility : facilitiesTrue) {
            sqlInsertDataInTable = "INSERT INTO ETICKET.ERIP_PAYMENT_FACILITIES VALUES (:id, :type , :name , :url)";

            Map namedParameters = new HashMap();
            namedParameters.put("id", id);
            namedParameters.put("type", facility.getType());
            namedParameters.put("name", facility.getName());
            namedParameters.put("url", facility.getUrl());

            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
            id ++;
        }

    }

    @Test
    public void testGetPaymentSystemList() {

        ResponseEntity<EripFacilities[]> system = restTemplate.getForEntity(
                "/" + version + "/directories/erip-facilities?inm=test", EripFacilities[].class);
        facilitiesFake = Arrays.asList(system.getBody());

        assertEquals(facilitiesTrue, facilitiesFake);

        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.ERIP_PAYMENT_FACILITIES");
    }
}


