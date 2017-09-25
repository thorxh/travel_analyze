package com.bonc.usdp.system;

/**
 * @author wanghui
 */
public class Constants {

    public static double TRAVEL_DATE_RATE = 0.5;
    public static double TRAVEL_TYPE_RATE = 0.1;
    public static double TRAVEL_ORIGIN_RATE = 0.3;
    public static double TRAVEL_DESTINATION_RATE = 0.5;
    public static String ORIGINAL_TRAVEL_INFORMATION_PATH = "data/plane_travel.txt";

    static String CONFIG_FILE_NAME = "config.properties";

    static String SYSTEM_PARAM_MINCLUSTER = "system.param.mincluster";
    static String SYSTEM_PARAM_MAXDISTANCE = "system.param.maxdistance";
    static String SYSTEM_PARAM_MAXDAYGAP = "system.param.maxdaygap";
    static String SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE = "system.param.maxcitydistance.departure";
    static String SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION = "system.param.maxcitydistance.destination";
    static String SYSTEM_PARAM_RECORDLIMIT = "system.param.recordlimit";
    static String SYSTEM_PARAM_THREADSIZE = "system.param.threadsize";

    static String TRAVEL_TYPE = "travel.type";

    static String DB_URL_SUFFIX = ".config.db.url";
    static String DB_DRIVER_SUFFIX = ".config.db.driver";
    static String DB_USERNAME_SUFFIX = ".config.db.username";
    static String DB_PASSWORD_SUFFIX = ".config.db.password";

    static String TABLE_NAME_SUFFIX = ".info.table.travellerName";
    static String TABLE_FIELD_TRAVELLERNAME_SUFFIX = ".info.table.field.travellername";
    static String TABLE_FIELD_TRAVELLERID_SUFFIX = ".info.table.field.travellerid";
    static String TABLE_FIELD_COMPANYNAME_SUFFIX = ".info.table.field.companyname";
    static String TABLE_FIELD_TRAFFICNUMBER_SUFFIX = ".info.table.field.trafficnumber";
    static String TABLE_FIELD_DEPARTURE_SUFFIX = ".info.table.field.departure";
    static String TABLE_FIELD_DESTINATION_SUFFIX = ".info.table.field.destination";
    static String TABLE_FIELD_DEPARTURETIME_SUFFIX = ".info.table.field.departuretime";
    static String TABLE_FIELD_ARRIVALTIME_SUFFIX = ".info.table.field.arrivaltime";
    static String TABLE_FIELD_TRAVELDATE_SUFFIX = ".info.table.field.traveldate";

}
