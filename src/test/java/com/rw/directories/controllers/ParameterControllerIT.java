package com.rw.directories.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rw.directories.dto.Parameter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ParameterControllerIT {

    private static final String PATH = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForParameterIT";

    @Value("${service.version}")
    String version;

    @Autowired
    public ParameterController parameterController;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;

    @JsonProperty
    public List<Parameter> parametersTrue, parametersFake;
    private MockMvc mockMvc;


    @Before
    public void setUp() throws IOException {



        this.mockMvc = webAppContextSetup(this.wac).build();

        parametersTrue = new ArrayList<>();
        parametersFake = new ArrayList<>();

        //переделать на ввод из файла
        parametersTrue.add(new Parameter(1, "c", "qwertyuytrewqwertyuijhgfdsaqwert", "v"));
        parametersTrue.add(new Parameter(2, "s", "s", "w"));


        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);

        List<String> lines = Files.readAllLines(Paths.get(PATH));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }
        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PARAMETERS");
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable = new String();

        for (Parameter param : parametersTrue) {

            sqlInsertDataInTable = "INSERT INTO ETICKET.PARAMETERS VALUES(:id , :category , :code , :value)";

            Map namedParameters = new HashMap();
            namedParameters.put("id", param.getId());
            namedParameters.put("category", param.getCategory());
            namedParameters.put("code", param.getCode());
            namedParameters.put("value", param.getValue());

            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);

        }

    }

    @Test
    public void testGetParameters() throws UnknownHostException {

        ResponseEntity<Parameter[]> params = restTemplate.getForEntity(
                "/" + version + "/directories/parameters?inm=test", Parameter[].class);
        parametersFake = Arrays.asList(params.getBody());
        assertTrue(parametersFake.equals(parametersTrue));
    }

    @Test
    public void getParameterByCode() {

        List<String> codes = new ArrayList();



        //переделать на ввод из файла
        codes.add("");
        codes.add(null);
        codes.add("s");
        codes.add("qwertyuytrewqwertyuijhgfdsaqwert");
        codes.add("qwertyuytrewqwertyuijhgfdsaqwertq");


            for (String code: codes){

            callQueryToServer(code);

            if ( code == null){
                assertTrue(parametersFake.get(0).getValue() == null);
            };

            if ( code == "" ){
                assertTrue(parametersFake.get(0).getValue() == "");
            };

            if (code != null && code != "") {

                if (code.length() > 32) {
                };                ;
                if (code.length() > 0 && code.length() < 32) {
                };
            }

        }

    }



    private void callQueryToServer(String code){

        MessageFormat messageFormat = new MessageFormat("/{0} /directories/parameter/{1}?inm=test");
        Object[] objArray = {version, code};
        String url = messageFormat.format(objArray);

        if (code == "") {

            try {
                parametersFake = Arrays.asList(restTemplate.getForEntity(url , Parameter.class).getBody());
            }catch (HttpClientErrorException e){
                parametersFake.add(new Parameter(1,"","",""));
            };

        }
        else {
            parametersFake = Arrays.asList(restTemplate.getForEntity(url, Parameter.class).getBody());
        }
    }

}
