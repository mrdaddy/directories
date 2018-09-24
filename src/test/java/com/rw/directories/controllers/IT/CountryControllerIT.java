package com.rw.directories.controllers.IT;

import com.rw.directories.dto.Country;
import com.rw.directories.utils.DBUtils;
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

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryControllerIT {

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;

    private MockMvc mockMvc;
    public List<Country> countriesTrue, countriesFake;

    private static final String PATH_COUNTRY_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForCountryIT";
    private static final String PATH_COUNTRY_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForCountryIT";
    @Before
    public void setUp() throws IOException {

        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
        countriesTrue = new ArrayList<>();
        countriesFake = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(PATH_COUNTRY_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreateCountry = strLine.split(" ");
                countriesTrue.add(new Country(argumentsForCreateCountry[0],
                        argumentsForCreateCountry[1],
                        DBUtils.toBoolean(Integer.parseInt(argumentsForCreateCountry[2])),
                        Integer.parseInt(argumentsForCreateCountry[3]),
                        Integer.parseInt(argumentsForCreateCountry[4])));
            }
        } catch (IOException e) {
            System.out.println("Input COUNTRY data error. Initialization failed");
        }

        List<String> lines = Files.readAllLines(Paths.get(PATH_COUNTRY_SQL));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }

        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.COUNTRIES");
        }catch (BadSqlGrammarException ex){}
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable = new String();

        for (Country country : countriesTrue) {
            sqlInsertDataInTable = "INSERT INTO ETICKET.COUNTRIES(CODE , NAME_EN, NAME_RU , IS_GLOBAL_PRICE , FREE_TICKET_AGE, CHILDREN_TICKET_AGE) " +
                    " VALUES (:CODE , :NAME_EN , :NAME_EN , :IS_GLOBAL_PRICE , :FREE_TICKET_AGE , :CHILDREN_TICKET_AGE)";

            Map namedParameters = new HashMap();
            namedParameters.put("CODE", country.getCode());
            namedParameters.put("NAME_EN", country.getName());
            namedParameters.put("NAME_RU", country.getName());
            namedParameters.put("IS_GLOBAL_PRICE", DBUtils.toString(country.isGlobalPrice()));
            namedParameters.put("FREE_TICKET_AGE", country.getFreeTicketAge());
            namedParameters.put("CHILDREN_TICKET_AGE", country.getChildrenTicketAge());


            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
        }
    }

    @Test
    public void testGetParametersList() {

        ResponseEntity<Country[]> countries = restTemplate.getForEntity(
                "/" + version + "/directories/countries?lang=en&inm=test", Country[].class);
        countriesFake = Arrays.asList(countries.getBody());
        assertTrue(countriesFake.equals(countriesTrue));
    }
}


