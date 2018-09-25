package com.rw.directories.controllers.IT;

import com.rw.directories.dto.Parameter;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ParameterControllerIT {

    private static final String PATH_PARAMETER_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForParameterIT";
    private static final String PATH_PARAMETER_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForParameterIT";

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;

    public List<Parameter> parametersTrue, parametersFake;
    public Parameter returnedParameter, parentParameter, parameterTrueDefault;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws IOException {


        this.mockMvc = webAppContextSetup(this.wac).build();

        parametersTrue = new ArrayList<>();
        parametersFake = new ArrayList<>();

        try{
            FileInputStream fstream = new FileInputStream(PATH_PARAMETER_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                String[] argumentsForCreateParameters = strLine.split(" ");
                parametersTrue.add(new Parameter(Parameter.CATEGORY.valueOf(argumentsForCreateParameters[0]), argumentsForCreateParameters[1],argumentsForCreateParameters[2]));
            }
        }catch (IOException e){
            System.out.println("Input PARAMETERS data error. Initialization failed");
        }
        parameterTrueDefault = new Parameter(null,"EMPTY_RESULT",null);

        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);

        List<String> lines = Files.readAllLines(Paths.get(PATH_PARAMETER_SQL));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }
        try{
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PARAMETERS");
        }catch (BadSqlGrammarException ex){}
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable;

        int id = 1;
        for (Parameter param : parametersTrue) {

            sqlInsertDataInTable = "INSERT INTO ETICKET.PARAMETERS VALUES( :id , :category , :code , :value)";

            Map namedParameters = new HashMap();
            namedParameters.put("id", String.valueOf(id));
            namedParameters.put("category", param.getCategory());
            namedParameters.put("code", param.getCode());
            namedParameters.put("value", param.getValue());

            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
            id++;
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
        List<Boolean> tests = new ArrayList<>();
        boolean trigger = true;

        try{
            FileInputStream fstream = new FileInputStream("/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/CodesForParameterIT");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                codes.add(strLine);
            }
        }catch (IOException e){
            System.out.println("Codes initialization failed.");
        }


        for (String code : codes) {

            parentParameter = parameterTrueDefault;
            for (Parameter param : parametersTrue) {

                if (param.getCode().equals(code)) {
                    parentParameter = param;
             //   }else if(parentParameter.equals(parameterTrueDefault)){
              //      parentParameter = parameterTrueDefault;
                }
            }

            callQueryToServer(code);

            if (code == null || code.equals("")) {
                if (returnedParameter.getCode().equals("EMPTY_RESULT"))
                    tests.add(true);
                else {
                    tests.add(false);
                }
            }

            if (code != null && code != "") {

                if (code.length() > 32) {
                    if (returnedParameter.getCode().equals("INVALID_ARGUMENT")) {
                        tests.add(true);
                    } else {
                        tests.add(false);
                    }
                }

                if (code.length() > 0 && code.length() < 32) {
                    if (returnedParameter.equals(parentParameter)) {
                        tests.add(true);
                    } else {
                        tests.add(false);
                    }
                }
            }
        }

        for (Boolean test: tests){
            if(!test){
                trigger = false;
            }
        }

        assertTrue(trigger);
}


    private void callQueryToServer(String code) {

        MessageFormat messageFormat = new MessageFormat("/{0} /directories/parameter/{1}?inm=test");
        Object[] objArray = {version, code};
        String url = messageFormat.format(objArray);

        if (code.equals("")) {
            try {
                returnedParameter = restTemplate.getForEntity(url, Parameter.class).getBody();
            } catch (HttpClientErrorException e) {
                returnedParameter = new Parameter(Parameter.CATEGORY.E, "EMPTY_RESULT", "");
            }

        } else {
            returnedParameter = restTemplate.getForEntity(url, Parameter.class).getBody();
        }
    }

}
