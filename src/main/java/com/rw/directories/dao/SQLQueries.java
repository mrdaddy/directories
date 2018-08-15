package com.rw.directories.dao;

public interface SQLQueries {
    String PARAMS_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, TYPE, CODE, VALUE FROM ETICKET.PARAMETERS";
    String PARAM_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, TYPE, CODE, VALUE FROM ETICKET.PARAMETERS WHERE CODE=:CODE";

    String COUNTRIES_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, CODE, TICKET_SELLING_ALLOWED, TIME_ZONE, NAME_{0} AS NAME, DEPARTURE_MSG_{0} AS DEPARTURE_MSG, ARRIVAL_MSG_{0} AS ARRIVAL_MSG, STATION_SELECT_PRIORITY, IS_GLOBAL_PRICE, FREE_TICKET_AGE, CHILDREN_TICKET_AGE FROM ETICKET.COUNTRIES";
    String PASSENGER_COUNTRIES_INFO = "SELECT CURRENT_TIMESTAMP AS UPDATED_ON, ID, ISO_CODE, NAME_{0} AS NAME FROM ETICKET.PASSENGER_COUNTRIES ORDER BY NAME_{0}";

    String DIRECTORY_UPDATES_INFO =
            "SELECT 'Countries' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.COUNTRIES " +
            "UNION " +
            "SELECT 'Parameters' AS DIRECTORY, MAX(ID) AS ID, CURRENT_TIMESTAMP AS UPDATED_ON FROM ETICKET.PARAMETERS " ;

}
