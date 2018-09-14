package com.rw.directories.controllers;

import com.rw.directories.BooleanTransformer;
import com.rw.directories.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
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
public class DirectoryUpdateControllerIT {

    @Value("${service.version}")
    String version;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DirectoryUpdateController directoryUpdateController;

    @Autowired
    private WebApplicationContext wac;
    private RestTemplate restTemplate;

    private BooleanTransformer booleanTransformer;
    private MockMvc mockMvc;
    public List<Country> countriesTrue;
    public List<DocumentType> typesTrue;
    public List<Parameter> parametersTrue;
    public List<PassengerCountry> passengersTrue;
    public List<DirectoryUpdate> updatesTrue, updatesFake;
    public List<PaymentSystem> systemsTrue;
    public List<EripFacilities> facilitiesTrue;


    private static final String PATH_COUNTRY_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForCountryIT";
    private static final String PATH_PARAMETER_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForParameterIT";
    private static final String PATH_DOCUMENT_TYPE_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForDocumentTypeIT";
    private static final String PATH_PASSENGER_COUNTRY_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForPassengerCountryIT";
    private static final String PATH_PAYMENT_SYSTEMS_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForPaymentSystemIT";
    private static final String PATH_ERIP_FACILITIES_SQL = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/SQLforTest/CreateTableForEripFacilitiesIT";


    private static final String sqlInsertCountryDataInTable = "INSERT INTO ETICKET.COUNTRIES(CODE , NAME_EN, NAME_RU , IS_GLOBAL_PRICE , FREE_TICKET_AGE, CHILDREN_TICKET_AGE) " +
            " VALUES (:code , :name_en , :name_en , :is_global_price , :free_ticket_age , :children_ticket_age)";
    private static final String sqlInsertParameterDataInTable = "INSERT INTO ETICKET.PARAMETERS VALUES(:id , :category , :code , :value)";
    private static final String sqlInsertPassengerCountryDataInTable = "INSERT INTO ETICKET.PASSENGER_COUNTRIES VALUES (:id  , :iso_code , :name , :name )";
    private static final String sqlInsertDocumentTypeDataInTable = "INSERT INTO ETICKET.DOCUMENT_TYPES(ID , CODE, EXPRESS_CODE , NAME_RU , NAME_EN, STATUS, USE_FOR_ET, IS_GP_USED) " +
            " VALUES (:id, :code ,:express_code , :name_en , :name_en , :status , :use_for_ET , :is_GP_used)";

    private static final String sqlInsertPaymentSystemDataInTable = "INSERT INTO ETICKET.PAYMENT_SYSTEMS VALUES (:id, :type , :name , :name, :shortName , :shortName , :isDefault , :isAccessebleRU, " +
            ":isAccessebleEN, :url, :time, :systemCode, :systemName, :systemURL, :systemCancelURL, :additional, :payAgent, :ticketReturn, :ticketReturnOnline)";
    private static final String sqlInsertEripFacilitiesDataInTable = "INSERT INTO ETICKET.ERIP_PAYMENT_FACILITIES VALUES (:id, :type , :name , :url)";



    private static final String PATH_PARAMETER_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForParameterIT";
    private static final String PATH_COUNTRY_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForCountryIT";
    private static final String PATH_DOCUMENT_TYPE_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForDocumentTypeIT";
    private static final String PATH_PASSENGER_COUNTRY_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForPassengerCountryIT";
    private static final String PATH_PAYMENT_SYSTEMS_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForPaymentSystemIT";
    private static final String PATH_ERIP_FACILITIES_DATA = "/Volumes/Files/MyFiles/programming/IBA/bel_chigunka/src/test/resources/DataForEripFacilitiesIT";


    @Before
    public void setUp() throws IOException {


        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
        booleanTransformer = new BooleanTransformer();
        countriesTrue = new ArrayList<>();
        typesTrue = new ArrayList<>();
        parametersTrue = new ArrayList<>();
        passengersTrue = new ArrayList<>();
        systemsTrue = new ArrayList<>();
        facilitiesTrue = new ArrayList<>();
        String strLine;

        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PARAMETERS");
        } catch (BadSqlGrammarException ex) {
            System.out.println("Table doesn't exist.The program continues to work");
        }
        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.COUNTRIES");
        } catch (BadSqlGrammarException ex) {
            System.out.println("Table doesn't exist.The program continues to work");
        }
        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.DOCUMENT_TYPES");
        } catch (BadSqlGrammarException ex) {
            System.out.println("Table doesn't exist.The program continues to work");
        }
        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PASSENGER_COUNTRIES");
        } catch (BadSqlGrammarException ex) {
            System.out.println("Table doesn't exist.The program continues to work");
        }
        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PAYMENT_SYSTEMS");
        } catch (BadSqlGrammarException ex) {
            System.out.println("Table doesn't exist.The program continues to work");
        }
        try {
            namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.ERIP_PAYMENT_FACILITIES");
        } catch (BadSqlGrammarException ex) {
        }

        try {
            FileInputStream fstream = new FileInputStream(PATH_PARAMETER_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreateParameters = strLine.split(" ");
                parametersTrue.add(new Parameter(argumentsForCreateParameters[0], argumentsForCreateParameters[1], argumentsForCreateParameters[2]));
            }
        } catch (IOException e) {
            System.out.println("Input PARAMETERS data error. Initialization failed");
        }

        try {
            FileInputStream fstream = new FileInputStream(PATH_DOCUMENT_TYPE_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
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

        try {
            FileInputStream fstream = new FileInputStream(PATH_PASSENGER_COUNTRY_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreatePassengersCountry = strLine.split(" ");
                passengersTrue.add(new PassengerCountry(argumentsForCreatePassengersCountry[0], argumentsForCreatePassengersCountry[1]));
            }
        } catch (IOException e) {
            System.out.println("Input PASSENGER_COUNTRY data error. Initialization failed");
        }

        try {
            FileInputStream fstream = new FileInputStream(PATH_COUNTRY_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreateCountry = strLine.split(" ");
                countriesTrue.add(new Country(argumentsForCreateCountry[0],
                        argumentsForCreateCountry[1],
                        booleanTransformer.transformToBoolean(argumentsForCreateCountry[2]),
                        Integer.parseInt(argumentsForCreateCountry[3]),
                        Integer.parseInt(argumentsForCreateCountry[4])));
            }
        } catch (IOException e) {
            System.out.println("Input COUNTRY data error. Initialization failed");
        }

        try {
            FileInputStream fstream = new FileInputStream(PATH_PAYMENT_SYSTEMS_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreatePaySystem = strLine.split(",");
                systemsTrue.add(new PaymentSystem(argumentsForCreatePaySystem[0],
                        argumentsForCreatePaySystem[1], argumentsForCreatePaySystem[2],
                        booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[3]), argumentsForCreatePaySystem[4], Integer.parseInt(argumentsForCreatePaySystem[5]),
                        argumentsForCreatePaySystem[6], argumentsForCreatePaySystem[7], argumentsForCreatePaySystem[8], argumentsForCreatePaySystem[9],
                        argumentsForCreatePaySystem[10], booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[11]),
                        booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[12]), argumentsForCreatePaySystem[13],
                        booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[14]), booleanTransformer.transformToBoolean(argumentsForCreatePaySystem[15])));
            }
        } catch (IOException e) {
            System.out.println("Input PAYMENT_SYSTEMS data error. Initialization failed");
        }

        try {
            FileInputStream fstream = new FileInputStream(PATH_ERIP_FACILITIES_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((strLine = br.readLine()) != null) {
                String[] argumentsForCreateEripFacilities = strLine.split(",");
                facilitiesTrue.add(new EripFacilities(argumentsForCreateEripFacilities[0],
                        argumentsForCreateEripFacilities[1],argumentsForCreateEripFacilities[2]));}
        } catch (IOException e) {
            System.out.println("Input ERIP_FACILITIES data error. Initialization failed");
        }


        List<String> pathList = new ArrayList<>();
        pathList.add(PATH_COUNTRY_SQL);
        pathList.add(PATH_PARAMETER_SQL);
        pathList.add(PATH_DOCUMENT_TYPE_SQL);
        pathList.add(PATH_PASSENGER_COUNTRY_SQL);
        pathList.add(PATH_PAYMENT_SYSTEMS_SQL);
        pathList.add(PATH_ERIP_FACILITIES_SQL);

        for (String path : pathList) {

            List<String> lines = Files.readAllLines(Paths.get(path));
            String sqlCreateTable = new String();

            for (String string : lines) {
                sqlCreateTable += string;
            }

            namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlCreateTable);
        }
        int id = 1;
        for (Parameter parameter : parametersTrue) {
            Map namedParametersForParameter = new HashMap();

            namedParametersForParameter.put("id", id);
            namedParametersForParameter.put("category", parameter.getCategory());
            namedParametersForParameter.put("code", parameter.getCode());
            namedParametersForParameter.put("value", parameter.getValue());

            namedParameterJdbcTemplate.update(sqlInsertParameterDataInTable, namedParametersForParameter);
            id++;
        }

        for (Country country : countriesTrue) {
            Map namedParametersForCountry = new HashMap();

            namedParametersForCountry.put("code", country.getCode());
            namedParametersForCountry.put("name_en", country.getName());
            namedParametersForCountry.put("name_ru", country.getName());
            namedParametersForCountry.put("is_global_price", booleanTransformer.transformToChar(country.isGlobalPrice()));
            namedParametersForCountry.put("free_ticket_age", country.getFreeTicketAge());
            namedParametersForCountry.put("children_ticket_age", country.getChildrenTicketAge());


            namedParameterJdbcTemplate.update(sqlInsertCountryDataInTable, namedParametersForCountry);
        }

        id = 1;
        for (PassengerCountry passengerCountry : passengersTrue) {
            Map namedParametersForDocumentType = new HashMap();

            namedParametersForDocumentType.put("id", id);
            namedParametersForDocumentType.put("iso_code", passengerCountry.getIsoCode());
            namedParametersForDocumentType.put("name", passengerCountry.getName());

            namedParameterJdbcTemplate.update(sqlInsertPassengerCountryDataInTable, namedParametersForDocumentType);
            id++;
        }

        id = 1;
        for (DocumentType type : typesTrue) {

            Map namedParametersForDocumentType = new HashMap();

            namedParametersForDocumentType.put("id", id);
            namedParametersForDocumentType.put("code", type.getCode());
            namedParametersForDocumentType.put("express_code", type.getExpressCode());
            namedParametersForDocumentType.put("name_en", type.getName());
            namedParametersForDocumentType.put("status", type.getStatus());
            namedParametersForDocumentType.put("use_for_ET", type.getUseForET());
            namedParametersForDocumentType.put("is_GP_used", booleanTransformer.transformToChar(type.isUsedForGlobalPrice()));

            namedParameterJdbcTemplate.update(sqlInsertDocumentTypeDataInTable, namedParametersForDocumentType);
            id++;
        }

        id = 1;
        for (PaymentSystem system : systemsTrue) {

            Map namedParameters = new HashMap();
            namedParameters.put("id", id);
            namedParameters.put("type", system.getType());
            namedParameters.put("name", system.getName());
            namedParameters.put("shortName", system.getName());
            namedParameters.put("isDefault", booleanTransformer.transformToChar(system.isDefault()));
            namedParameters.put("isAccessebleRU", booleanTransformer.transformToChar(system.isAccessibleRU()));
            namedParameters.put("isAccessebleEN", booleanTransformer.transformToChar(system.isAccessibleEN()));
            namedParameters.put("url", system.getUrl());
            namedParameters.put("time", system.getTime());
            namedParameters.put("systemCode", system.getSystemPayCode());
            namedParameters.put("systemName", system.getSystemPayName());
            namedParameters.put("systemURL", system.getSystemPayUrl());
            namedParameters.put("systemCancelURL", system.getSystemPayCancelUrl());
            namedParameters.put("additional", system.getAdditionalParams());
            namedParameters.put("payAgent", system.getPayingAgent());
            namedParameters.put("ticketReturn", booleanTransformer.transformToChar(system.isTicketReturnUnable()));
            namedParameters.put("ticketReturnOnline", booleanTransformer.transformToChar(system.isOnlineReturnUnable()));

            namedParameterJdbcTemplate.update(sqlInsertPaymentSystemDataInTable, namedParameters);
            id++;
        }

        for (EripFacilities facility : facilitiesTrue) {

            Map namedParameters = new HashMap();
            namedParameters.put("id", id);
            namedParameters.put("type", facility.getType());
            namedParameters.put("name", facility.getName());
            namedParameters.put("url", facility.getUrl());

            namedParameterJdbcTemplate.update(sqlInsertEripFacilitiesDataInTable, namedParameters);
            id ++;
        }
    }


    @Test
    public void testGetParametersList() {

        updatesTrue = directoryUpdateController.getDirectoryUpdates("test");
        updatesFake = Arrays.asList(restTemplate.getForEntity( /////ЛОВИТ EXCEPTION
                "/" + version + "/directories/dir-updates?inm=test", DirectoryUpdate[].class).getBody());


        boolean check = true;

        if (updatesFake.size() == updatesTrue.size()) {
            for (int i = 0; i < updatesFake.size(); i++) {
                if (!updatesFake.get(i).getDirectory().equals(updatesTrue.get(i).getDirectory())) {
                    check = false;
                }
            }
        } else {
            check = false;
        }

        assertTrue(check);


        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PARAMETERS");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.COUNTRIES");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.DOCUMENT_TYPES");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PASSENGER_COUNTRIES");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("DROP TABLE ETICKET.PAYMENT_SYSTEMS");
    }
}


