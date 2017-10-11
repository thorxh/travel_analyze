package com.bonc.usdp.system;

/**
 * @author wanghui
 */
public class Constants {

    public static double DEGREE_UNIT = Math.PI / 180.0;
    public static long ONE_DAY_MILLISECOND = 86400000;

    static String CONFIG_FILE_NAME = "config.properties";

    static String SYSTEM_PARAM_MINCLUSTER = "system.param.mincluster";
    static String SYSTEM_PARAM_MAXDISTANCE = "system.param.maxdistance";
    static String SYSTEM_PARAM_MAXDAYGAP = "system.param.maxdaygap";
    static String SYSTEM_PARAM_CHARACTER_SWITCH = "system.param.character.switch";
    static String SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE = "system.param.maxcitydistance.departure";
    static String SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION = "system.param.maxcitydistance.destination";
    static String SYSTEM_PARAM_RECORDLIMIT = "system.param.recordlimit";
    static String SYSTEM_PARAM_THREADSIZE = "system.param.threadsize";
    static String SYSTEM_PARAM_PATTERN_TIME = "system.param.timepattern";
    static String SYSTEM_PARAM_PATTERN_DATE = "system.param.datepattern";

    static String SYSTEM_PARAM_MIN_SUP = "system.param.minsup";
    static String SYSTEM_PARAM_MIN_PARTNER_NUM = "system.param.minpartnernum";

    static String SYSTEM_RATE_TRAVELDATE = "system.rate.traveldate";
    static String SYSTEM_RATE_TRAVELTYPE = "system.rate.traveltype";
    static String SYSTEM_RATE_COMPANYNAME = "system.rate.companyname";
    static String SYSTEM_RATE_TRAFFICNUMBER = "system.rate.trafficnumber";
    static String SYSTEM_RATE_DEPARTURE = "system.rate.departure";
    static String SYSTEM_RATE_DESTINATION = "system.rate.destination";
    static String SYSTEM_RATE_DEPARTURETIME = "system.rate.departuretime";
    static String SYSTEM_RATE_ARRIVALTIME = "system.rate.arrivaltime";

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
