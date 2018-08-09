package com.rw.directories.dao;

public interface SQLQueries {
    public static final String PARAMS_INFO = "SELECT * FROM ETICKET.PARAMETERS WITH UR";
    public static final String COUNTRIES_INFO = "SELECT * FROM ETICKET.COUNTRIES WITH UR";
    public static final String PARAM_INFO = "SELECT * FROM ETICKET.PARAMETERS WHERE CODE=:CODE WITH UR";

}
