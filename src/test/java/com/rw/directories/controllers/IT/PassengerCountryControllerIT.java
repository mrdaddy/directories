package com.rw.directories.controllers.IT;

import com.rw.directories.dto.PassengerCountry;
import com.rw.directories.utils.LanguageUtils;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PassengerCountryControllerIT {

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;

    private MockMvc mockMvc;
    public List<PassengerCountry> passengersTrue, passengersFake;

    private static final String PATH_PASSENGER_COUNTRY_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForPassengerCountryIT";
    private static final String PATH_PASSENGER_COUNTRY_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForPassengerCountryIT";


    @Before
    public void setUp() throws IOException {


        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);

        passengersFake = new ArrayList<>();
        passengersTrue = new ArrayList<>();

        try {
            FileInputStream fstream = new FileInputStream(PATH_PASSENGER_COUNTRY_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                PassengerCountry country = new PassengerCountry();
                country.setName(jsonArray.getJSONObject(i).getString("name"));
                country.setIsoCode(jsonArray.getJSONObject(i).getString("isoCode"));
                passengersTrue.add(country);
            }
        } catch (IOException e) {
            System.out.println("Input PASSENGER_COUNTRY data error. Initialization failed");
        }

        List<String> lines = Files.readAllLines(Paths.get(PATH_PASSENGER_COUNTRY_SQL));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }

        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PASSENGER_COUNTRIES");
        } catch (BadSqlGrammarException ex) {}
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable;

        for (PassengerCountry passengerCountry : passengersTrue) {
            sqlInsertDataInTable = "INSERT INTO ETICKET.PASSENGER_COUNTRIES(ISO_CODE, NAME_RU, NAME_EN) VALUES (:iso_code , :name , :name )";

            Map namedParameters = new HashMap();
            namedParameters.put("iso_code", passengerCountry.getIsoCode());
            namedParameters.put("name", passengerCountry.getName());

            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
        }
    }

    @Test
    public void getPassengerCountries() {

        ResponseEntity<PassengerCountry[]> passengerCountries = restTemplate.getForEntity(
                "/" + version + "/directories/pass-countries?lang=" + LanguageUtils.SUPPORTED_LANGUAGES.en + "&inm=test", PassengerCountry[].class);
        passengersFake = Arrays.asList(passengerCountries.getBody());


        assertEquals(passengersTrue,passengersFake);

        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PASSENGER_COUNTRIES");
    }

}





