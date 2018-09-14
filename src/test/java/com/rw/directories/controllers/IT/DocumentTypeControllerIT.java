package com.rw.directories.controllers.IT;

import com.rw.directories.BooleanTransformer;
import com.rw.directories.dto.DocumentType;
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
public class DocumentTypeControllerIT {

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate;
    private BooleanTransformer booleanTransformer;
    private MockMvc mockMvc;
    public List<DocumentType> typesTrue, typesFake;

    private static final String PATH_DOCUMENT_TYPE_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForDocumentTypeIT";
    private static final String PATH_DOCUMENT_TYPE_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForDocumentTypeIT";

    @Before
    public void setUp() throws IOException {

        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
        booleanTransformer = new BooleanTransformer();
        typesTrue = new ArrayList<>();
        typesFake = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(PATH_DOCUMENT_TYPE_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreateDocumentType = strLine.split(" ");
                typesTrue.add(new DocumentType(argumentsForCreateDocumentType[0],
                        argumentsForCreateDocumentType[1], argumentsForCreateDocumentType[2],
                        Integer.parseInt(argumentsForCreateDocumentType[3]),
                        booleanTransformer.transformToBoolean(argumentsForCreateDocumentType[4]), argumentsForCreateDocumentType[5]));
            }
        } catch (IOException e) {
            System.out.println("Input DOCUMENT_TYPE data error. Initialization failed");
        }

        List<String> lines = Files.readAllLines(Paths.get(PATH_DOCUMENT_TYPE_SQL));
        String sqlCreateTable = new String();

        for (String string : lines) {
            sqlCreateTable += string;
        }

        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.DOCUMENT_TYPES");
        }catch (BadSqlGrammarException ex){}
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);

        String sqlInsertDataInTable;

        int id = 1;
        for (DocumentType type : typesTrue) {
            sqlInsertDataInTable = "INSERT INTO ETICKET.DOCUMENT_TYPES(ID , CODE, EXPRESS_CODE , NAME_RU , NAME_EN, STATUS, USE_FOR_ET, IS_GP_USED) " +
                    " VALUES (:ID, :CODE ,:EXPRESS_CODE , :NAME_EN , :NAME_EN , :STATUS , :USE_FOR_ET , :IS_GP_USED)";

            Map namedParameters = new HashMap();

            namedParameters.put("ID", id);
            namedParameters.put("CODE", type.getCode());
            namedParameters.put("EXPRESS_CODE", type.getExpressCode());
            namedParameters.put("NAME_EN", type.getName());
            namedParameters.put("STATUS", type.getStatus());
            namedParameters.put("USE_FOR_ET", type.getUseForET());

            namedParameters.put("IS_GP_USED", booleanTransformer.transformToChar(type.isUsedForGlobalPrice()));
            namedParameterJdbcTemplate.update(sqlInsertDataInTable, namedParameters);
            id++;
        }
    }

    @Test
    public void testGetParametersList() {

        ResponseEntity<DocumentType[]> types = restTemplate.getForEntity(
                "/" + version + "/directories/doc-types?lang=en&inm=test", DocumentType[].class);
        typesFake = Arrays.asList(types.getBody());
        assertTrue(typesFake.equals(typesTrue));
    }
}
