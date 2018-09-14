package com.rw.directories.dao;

public interface SQLQueries {
    String PARAMS_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, TYPE, CODE, VALUE FROM ETICKET.PARAMETERS";
    String PARAM_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, TYPE, CODE, VALUE FROM ETICKET.PARAMETERS WHERE CODE=:CODE";

    String COUNTRIES_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, CODE, TICKET_SELLING_ALLOWED, TIME_ZONE, NAME_{0} AS NAME, DEPARTURE_MSG_{0} AS DEPARTURE_MSG, ARRIVAL_MSG_{0} AS ARRIVAL_MSG, STATION_SELECT_PRIORITY, IS_GLOBAL_PRICE, FREE_TICKET_AGE, CHILDREN_TICKET_AGE FROM ETICKET.COUNTRIES";
    String PASSENGER_COUNTRIES_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, ISO_CODE, NAME_{0} AS NAME FROM ETICKET.PASSENGER_COUNTRIES ORDER BY NAME_{0}";
    String DOCUMENT_TYPES_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, CODE, NAME_{0} AS NAME, STATUS, EXPRESS_CODE, IS_GP_USED, USE_FOR_ET FROM ETICKET.DOCUMENT_TYPES";

    String PAYMENT_SYSTEM_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID,  PAYMENT_SYSTEM_TYPE ,PAYMENT_SYSTEM_NAME_EN ,PAYMENT_SYSTEM_SN_EN ,IS_DEFAULT, IS_ACCESSIBLE_RU, IS_ACCESSIBLE_EN, PAYMENT_SYSTEM_URL, PAYMENT_TIME, ETICKET_CODE, ETICKET_NAME, ETICKET_URL, ETICKET_CANCEL_URL, ADDITIONAL_PARAMS, PAYING_AGENT,IS_TICKET_RETURN_ENABLE, IS_ONLINE_RETURN_ENABLE FROM ETICKET.PAYMENT_SYSTEMS";


    String ERIP_FACILITIES_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, FACILITY_TYPE, FACILITY_NAME,  FACILITY_URL FROM ETICKET.ERIP_PAYMENT_FACILITIES" ;

    String DIRECTORY_UPDATES_INFO =
                  "SELECT 'Countries' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.COUNTRIES " +
            "UNION SELECT 'Parameters' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.PARAMETERS " +
            "UNION SELECT 'PassengerCountries' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.PASSENGER_COUNTRIES " +
            "UNION SELECT 'DocumentTypes' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.DOCUMENT_TYPES " +
            "UNION SELECT 'PaymentSystems' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.PAYMENT_SYSTEMS";

}