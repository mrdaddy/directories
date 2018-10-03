package com.rw.directories.controllers.IT;

import com.rw.directories.controllers.DirectoryUpdateController;
import com.rw.directories.dto.*;
import com.rw.directories.utils.DBUtils;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Ignore;
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
import java.sql.Timestamp;
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

    private MockMvc mockMvc;
    Map namedParameters = new HashMap();

    public List<Country> countriesTrue;
    public List<DocumentType> typesTrue;
    public List<Parameter> parametersTrue;
    public List<PassengerCountry> passengersTrue;
    public List<DirectoryUpdate> updatesTrue, updatesFake;
    public List<PaymentSystem> systemsTrue;
    public List<EripFacilities> facilitiesTrue;


    private static final String PATH_COUNTRY_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForCountryIT";
    private static final String PATH_PARAMETER_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForParameterIT";
    private static final String PATH_DOCUMENT_TYPE_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForDocumentTypeIT";
    private static final String PATH_PASSENGER_COUNTRY_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForPassengerCountryIT";
    private static final String PATH_PAYMENT_SYSTEMS_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForPaymentSystemIT";
    private static final String PATH_ERIP_FACILITIES_SQL = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/SQLforTest/CreateTableForEripFacilitiesIT";


    private static final String sqlInsertCountryDataInTable = "INSERT INTO ETICKET.COUNTRIES(CODE , NAME_EN, NAME_RU , IS_GLOBAL_PRICE , FREE_TICKET_AGE, CHILDREN_TICKET_AGE, UPDATE_TIME) " +
            " VALUES (:code , :name_en , :name_en , :is_global_price , :free_ticket_age , :children_ticket_age, :update_time)";
    private static final String sqlInsertParameterDataInTable = "INSERT INTO ETICKET.PARAMETERS(TYPE, CODE, VALUE, UPDATE_TIME) VALUES( :category , :code , :value, :update_time)";
    private static final String sqlInsertPassengerCountryDataInTable = "INSERT INTO ETICKET.PASSENGER_COUNTRIES(ISO_CODE, NAME_RU, NAME_EN, UPDATE_TIME) VALUES ( :iso_code , :name , :name, :update_time )";
    private static final String sqlInsertDocumentTypeDataInTable = "INSERT INTO ETICKET.DOCUMENT_TYPES( CODE, EXPRESS_CODE , NAME_RU , NAME_EN, STATUS, USE_FOR_ET, IS_GP_USED, UPDATE_TIME) " +
            " VALUES ( :code ,:express_code , :name_en , :name_en , :status , :use_for_ET , :is_GP_used, :update_time)";

    private static final String sqlInsertPaymentSystemDataInTable = "INSERT INTO ETICKET.PAYMENT_SYSTEMS(PAYMENT_SYSTEM_TYPE,PAYMENT_SYSTEM_NAME_EN,PAYMENT_SYSTEM_NAME_RU, PAYMENT_SYSTEM_SN_EN, PAYMENT_SYSTEM_SN_RU,IS_DEFAULT," +
            "PAYMENT_TIME,ETICKET_CODE,ETICKET_NAME,ETICKET_URL,ETICKET_CANCEL_URL,IS_TICKET_RETURN_ENABLE,IS_ONLINE_RETURN_ENABLE, UPDATE_TIME) VALUES ( :type , :name , :name, :shortName , " +
            ":shortName , :isDefault , :time, :systemCode, :systemName, :systemURL, :systemCancelURL, :ticketReturn, :ticketReturnOnline, :update_time)";

    private static final String sqlInsertEripFacilitiesDataInTable = "INSERT INTO ETICKET.ERIP_PAYMENT_FACILITIES(FACILITY_TYPE, FACILITY_NAME, FACILITY_URL, UPDATE_TIME) VALUES (:type , :name , :url, :update_time)";



    private static final String PATH_PARAMETER_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForParameterIT";
    private static final String PATH_COUNTRY_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForCountryIT";
    private static final String PATH_DOCUMENT_TYPE_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForDocumentTypeIT";
    private static final String PATH_PASSENGER_COUNTRY_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForPassengerCountryIT";
    private static final String PATH_PAYMENT_SYSTEMS_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForPaymentSystemIT";
    private static final String PATH_ERIP_FACILITIES_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForEripFacilitiesIT";

    private static final String PATH_DIRECTORY_UPDATE_DATA = "/Volumes/Files/MyFiles/programming/IBA/directories/src/test/resources/DataForDirUpdatesIT";


    @Before
    public void setUp() throws IOException {

        this.mockMvc = webAppContextSetup(this.wac).build();
        MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
        restTemplate = new RestTemplate(requestFactory);
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

        try {
            FileInputStream fstream = new FileInputStream(PATH_PARAMETER_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }
             JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                Parameter parameter = new Parameter();
                parameter.setCode(jsonArray.getJSONObject(i).getString("code"));
                parameter.setValue(jsonArray.getJSONObject(i).getString("value"));
                parameter.setCategory(Parameter.CATEGORY.valueOf(jsonArray.getJSONObject(i).getString("category")));
                parameter.setUpdatedOn(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("updatedOn")));

                parametersTrue.add(parameter);

            }
        } catch (IOException e) {
            System.out.println("Input PARAMETERS data error. Initialization failed:\n" + e.getMessage());
        }


        try {
            FileInputStream fstream = new FileInputStream(PATH_DOCUMENT_TYPE_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                DocumentType docType = new DocumentType();
                docType.setCode(jsonArray.getJSONObject(i).getString("code"));
                docType.setName(jsonArray.getJSONObject(i).getString("name"));
                docType.setStatus(DocumentType.STATUS.valueOf(jsonArray.getJSONObject(i).getString("status")));
                docType.setUseForET(jsonArray.getJSONObject(i).getInt("useForET"));
                docType.setUsedForGlobalPrice(jsonArray.getJSONObject(i).getBoolean("usedForGlobalPrice"));
                docType.setExpressCode(jsonArray.getJSONObject(i).getString("expressCode"));
                docType.setUpdatedOn(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("updatedOn")));

                typesTrue.add(docType);

            }
        } catch (IOException e) {
            System.out.println("Input DOCUMENT_TYPE data error. Initialization failed:\n" + e.getMessage());
        }


        try {
            FileInputStream fstream = new FileInputStream(PATH_PASSENGER_COUNTRY_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                PassengerCountry passCountry = new PassengerCountry();
                passCountry.setName(jsonArray.getJSONObject(i).getString("name"));
                passCountry.setIsoCode(jsonArray.getJSONObject(i).getString("isoCode"));
                passCountry.setUpdatedOn(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("updatedOn")));

                passengersTrue.add(passCountry);

            }
        } catch (IOException e) {
            System.out.println("Input PASSENGER_COUNTRY data error. Initialization failed:\n" + e.getMessage());
        }



        try {
            FileInputStream fstream = new FileInputStream(PATH_COUNTRY_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                Country country = new Country();
                country.setCode(jsonArray.getJSONObject(i).getString("code"));
                country.setName(jsonArray.getJSONObject(i).getString("name"));
                country.setGlobalPrice(jsonArray.getJSONObject(i).getBoolean("globalPrice"));
                country.setFreeTicketAge(jsonArray.getJSONObject(i).getInt("freeTicketAge"));
                country.setChildrenTicketAge(jsonArray.getJSONObject(i).getInt("childrenTicketAge"));
                country.setUpdatedOn(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("updatedOn")));

                countriesTrue.add(country);

            }
        } catch (IOException e) {
            System.out.println("Input COUNTRY data error. Initialization failed:\n" + e.getMessage());
        }


        try {
            FileInputStream fstream = new FileInputStream(PATH_PAYMENT_SYSTEMS_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                PaymentSystem system = new PaymentSystem();
                system.setName(jsonArray.getJSONObject(i).getString("name"));
                system.setDefault(jsonArray.getJSONObject(i).getBoolean("default"));
                system.setType(PaymentSystem.PAYMENT_SYSTEM.valueOf(jsonArray.getJSONObject(i).getString("type")));
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
                system.setUpdatedOn(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("updatedOn")));

                systemsTrue.add(system);

            }
        } catch (IOException e) {
            System.out.println("Input PAYMENT_SYSTEMS data error. Initialization failed:\n" + e.getMessage());
        }


        try {
            FileInputStream fstream = new FileInputStream(PATH_ERIP_FACILITIES_DATA);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            StringBuilder builder = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                EripFacilities eripFacility = new EripFacilities();
                eripFacility.setName(jsonArray.getJSONObject(i).getString("name"));
                eripFacility.setType(jsonArray.getJSONObject(i).getString("type"));
                eripFacility.setUrl(jsonArray.getJSONObject(i).getString("url"));

                facilitiesTrue.add(eripFacility);
                eripFacility.setUpdatedOn(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("updatedOn")));
            }
        } catch (IOException e) {
            System.out.println("Input ERIP_FACILITIES data error. Initialization failed:\n" + e.getMessage());
        }


        for (Parameter parameter : parametersTrue) {
            Map namedParametersForParameter = new HashMap();

            namedParametersForParameter.put("category", parameter.getCategory().toString());
            namedParametersForParameter.put("code", parameter.getCode());
            namedParametersForParameter.put("value", parameter.getValue());
            namedParametersForParameter.put("update_time", parameter.getUpdatedOn());

            namedParameterJdbcTemplate.update(sqlInsertParameterDataInTable, namedParametersForParameter);
        }

        for (Country country : countriesTrue) {
            Map namedParametersForCountry = new HashMap();

            namedParametersForCountry.put("code", country.getCode());
            namedParametersForCountry.put("name_en", country.getName());
            namedParametersForCountry.put("name_ru", country.getName());
            namedParametersForCountry.put("is_global_price", DBUtils.toString(country.isGlobalPrice()));
            namedParametersForCountry.put("free_ticket_age", country.getFreeTicketAge());
            namedParametersForCountry.put("children_ticket_age", country.getChildrenTicketAge());
            namedParametersForCountry.put("update_time", country.getUpdatedOn());



            namedParameterJdbcTemplate.update(sqlInsertCountryDataInTable, namedParametersForCountry);
        }

        for (PassengerCountry passengerCountry : passengersTrue) {
            Map namedParametersForPassengersCountry = new HashMap();

            namedParametersForPassengersCountry.put("iso_code", passengerCountry.getIsoCode());
            namedParametersForPassengersCountry.put("name", passengerCountry.getName());
            namedParametersForPassengersCountry.put("update_time", passengerCountry.getUpdatedOn());

            namedParameterJdbcTemplate.update(sqlInsertPassengerCountryDataInTable, namedParametersForPassengersCountry);
        }

        for (DocumentType type : typesTrue) {

            Map namedParametersForDocumentType = new HashMap();

            namedParametersForDocumentType.put("code", type.getCode());
            namedParametersForDocumentType.put("express_code", type.getExpressCode());
            namedParametersForDocumentType.put("name_en", type.getName());
            namedParametersForDocumentType.put("status", type.getStatus().toString());
            namedParametersForDocumentType.put("use_for_ET", type.getUseForET());
            namedParametersForDocumentType.put("is_GP_used", DBUtils.toString(type.isUsedForGlobalPrice()));
            namedParametersForDocumentType.put("update_time", type.getUpdatedOn());


            namedParameterJdbcTemplate.update(sqlInsertDocumentTypeDataInTable, namedParametersForDocumentType);
        }

        for (PaymentSystem system : systemsTrue) {

            Map namedParametersForPaySystems = new HashMap();
            namedParametersForPaySystems.put("type", system.getType().toString());
            namedParametersForPaySystems.put("name", system.getName());
            namedParametersForPaySystems.put("shortName", system.getName());
            namedParametersForPaySystems.put("isDefault", DBUtils.toString(system.isDefault()));
            namedParametersForPaySystems.put("isAccessebleRU", true);
            namedParametersForPaySystems.put("isAccessebleEN", true);
            namedParametersForPaySystems.put("url", "");
            namedParametersForPaySystems.put("time", system.getPaymentTime());
            namedParametersForPaySystems.put("systemCode", system.getEticketCode());
            namedParametersForPaySystems.put("systemName", system.getEticketName());
            namedParametersForPaySystems.put("systemURL", system.getPaymentSuccessUrl());
            namedParametersForPaySystems.put("systemCancelURL", system.getPaymentErrorUrl());
            namedParametersForPaySystems.put("additional", "");
            namedParametersForPaySystems.put("payAgent", "");
            namedParametersForPaySystems.put("ticketReturn", DBUtils.toString(system.isTicketReturnEnabled()));
            namedParametersForPaySystems.put("ticketReturnOnline", DBUtils.toString(system.isOnlineReturnEnabled()));
            namedParametersForPaySystems.put("update_time", system.getUpdatedOn());

            namedParameterJdbcTemplate.update(sqlInsertPaymentSystemDataInTable, namedParametersForPaySystems);
        }

        for (EripFacilities facility : facilitiesTrue) {

            Map namedParametersForEripFacilities = new HashMap();
            namedParametersForEripFacilities.put("type", facility.getType());
            namedParametersForEripFacilities.put("name", facility.getName());
            namedParametersForEripFacilities.put("url", facility.getUrl());
            namedParametersForEripFacilities.put("update_time", facility.getUpdatedOn());


            namedParameterJdbcTemplate.update(sqlInsertEripFacilitiesDataInTable, namedParametersForEripFacilities);
        }

//        try {
//            FileInputStream fstream = new FileInputStream(PATH_DIRECTORY_UPDATE_DATA);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//            StringBuilder builder = new StringBuilder();
//            while ((strLine = br.readLine()) != null) {
//                builder.append(strLine);
//            }
//            JSONArray jsonArray = new JSONArray(builder.toString());
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                switch (jsonArray.getJSONObject(i).getString("directory")) {
//
//                    case "Countries":
//                        namedParameters.clear();
//                        namedParameters.put("lastUpdatedOn", jsonArray.getJSONObject(i).getString("lastUpdatedOn"));
//                        sqlString = " UPDATE ETICKET.COUNTRIES SET UPDATE_TIME = :lastUpdatedOn";
//                        namedParameterJdbcTemplate.update(sqlString, namedParameters);
//                        break;
//
//                    case "DocumentTypes":
//                        namedParameters.clear();
//                        namedParameters.put("lastUpdatedOn", jsonArray.getJSONObject(i).getString("lastUpdatedOn"));
//                        sqlString = "UPDATE ETICKET.DOCUMENT_TYPES SET UPDATE_TIME = :lastUpdatedOn";
//                        namedParameterJdbcTemplate.update(sqlString, namedParameters);
//                        break;
//
//                    case "PassengerCountries":
//                        namedParameters.clear();
//                        namedParameters.put("lastUpdatedOn", jsonArray.getJSONObject(i).getString("lastUpdatedOn"));
//                        sqlString = "UPDATE ETICKET.PASSENGER_COUNTRIES SET UPDATE_TIME = :lastUpdatedOn";
//                        namedParameterJdbcTemplate.update(sqlString, namedParameters);
//                        break;
//
//                    case "PaymentSystems":
//                        namedParameters.clear();
//                        namedParameters.put("lastUpdatedOn", jsonArray.getJSONObject(i).getString("lastUpdatedOn"));
//                        sqlString = "UPDATE ETICKET.PAYMENT_SYSTEMS SET UPDATE_TIME = :lastUpdatedOn";
//                        namedParameterJdbcTemplate.update(sqlString, namedParameters);
//                        break;
//
//                    case "Parameters":
//                        namedParameters.clear();
//                        namedParameters.put("lastUpdatedOn", jsonArray.getJSONObject(i).getString("lastUpdatedOn"));
//                        sqlString = "UPDATE ETICKET.PARAMETERS SET UPDATE_TIME = :lastUpdatedOn";
//                        namedParameterJdbcTemplate.update(sqlString, namedParameters);
//                        break;
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Input data error. Initialization failed:\n" + e.getMessage());
//        }
   }


        @Test
        public void testGetParametersList() {

            updatesFake = Arrays.asList(restTemplate.getForEntity("/" + version + "/directories/dir-updates?inm=Parameters", DirectoryUpdate[].class).getBody());
            updatesTrue = directoryUpdateController.getDirectoryUpdates("Parameters");


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


