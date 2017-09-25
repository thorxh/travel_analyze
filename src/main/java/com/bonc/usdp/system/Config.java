package com.bonc.usdp.system;

import com.bonc.usdp.entity.TableInfo;
import com.bonc.usdp.util.PathUtil;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * <br>Created by liyanjun@bonc.com.cn on 2017/9/21.<br><br>
 */
public class Config {

    public static int SYSTEM_PARAM_MINCLUSTER;
    public static double SYSTEM_PARAM_MAXDISTANCE;
    public static int SYSTEM_PARAM_RECORDLIMIT;
    public static int SYSTEM_PARAM_MAXDAYGAP;
    public static int SYSTEM_PARAM_THREADSIZE;
    public static int SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE;
    public static int SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION;
    public static List<TableInfo> tableInfos;

    private static Properties properties = new Properties();

    static {
        try(InputStream input = new BufferedInputStream(
                    new FileInputStream(PathUtil.getConfigPath() + File.separator + Constants.CONFIG_FILE_NAME))) {
            properties.load(input);

            String minCluster = properties.getProperty(Constants.SYSTEM_PARAM_MINCLUSTER, "1");
            SYSTEM_PARAM_MINCLUSTER = Integer.parseInt(minCluster);

            String maxDistance = properties.getProperty(Constants.SYSTEM_PARAM_MAXDISTANCE, "1");
            SYSTEM_PARAM_MAXDISTANCE = Double.parseDouble(maxDistance);

            String maxdaygap = properties.getProperty(Constants.SYSTEM_PARAM_MAXDAYGAP, "7");
            SYSTEM_PARAM_MAXDAYGAP = Integer.parseInt(maxdaygap);

            String threadSize = properties.getProperty(Constants.SYSTEM_PARAM_MAXDAYGAP, "0");
            SYSTEM_PARAM_THREADSIZE = Integer.parseInt(threadSize);
            if (SYSTEM_PARAM_THREADSIZE == 0) {
                SYSTEM_PARAM_THREADSIZE = Runtime.getRuntime().availableProcessors();
            }

            String maxCityDistanceDeparture = properties.getProperty(Constants.SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE, "500");
            SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE = Integer.parseInt(maxCityDistanceDeparture);

            String maxCityDistanceDestination = properties.getProperty(Constants.SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION, "500");
            SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION = Integer.parseInt(maxCityDistanceDestination);

            String recordLimit = properties.getProperty(Constants.SYSTEM_PARAM_RECORDLIMIT, "-1");
            SYSTEM_PARAM_RECORDLIMIT = Integer.parseInt(recordLimit);

            String trevalTypes = properties.getProperty(Constants.TRAVEL_TYPE);
            if (trevalTypes == null || trevalTypes.isEmpty()) {
                throw new RuntimeException("no travel type config found");
            }

            String[] types = trevalTypes.trim().split(",");
            if (types.length == 0) {
                throw new RuntimeException("no travel type config found");
            }

            tableInfos = new LinkedList<>();
            for (String type : types) {
                if (type == null) {
                    continue;
                }
                tableInfos.add(getTableInfo(type.trim()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static TableInfo getTableInfo(String travelType) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setDbUrl(properties.getProperty(travelType + Constants.DB_URL_SUFFIX));
        tableInfo.setDbDriver(properties.getProperty(travelType + Constants.DB_DRIVER_SUFFIX));
        tableInfo.setDbUserName(properties.getProperty(travelType + Constants.DB_USERNAME_SUFFIX));
        tableInfo.setDbPassword(properties.getProperty(travelType + Constants.DB_PASSWORD_SUFFIX));
        tableInfo.setTableName(properties.getProperty(travelType + Constants.TABLE_NAME_SUFFIX));
        tableInfo.setTravellerNameField(properties.getProperty(travelType + Constants.TABLE_FIELD_TRAVELLERNAME_SUFFIX));
        tableInfo.setTravellerIdField(properties.getProperty(travelType + Constants.TABLE_FIELD_TRAVELLERID_SUFFIX));
        tableInfo.setCompanyNameField(properties.getProperty(travelType + Constants.TABLE_FIELD_COMPANYNAME_SUFFIX));
        tableInfo.setTrafficNumberField(properties.getProperty(travelType + Constants.TABLE_FIELD_TRAFFICNUMBER_SUFFIX));
        tableInfo.setDepartureField(properties.getProperty(travelType + Constants.TABLE_FIELD_DEPARTURE_SUFFIX));
        tableInfo.setDestinationField(properties.getProperty(travelType + Constants.TABLE_FIELD_DESTINATION_SUFFIX));
        tableInfo.setDepartureTimeField(properties.getProperty(travelType + Constants.TABLE_FIELD_DEPARTURETIME_SUFFIX));
        tableInfo.setArrivalTimeField(properties.getProperty(travelType + Constants.TABLE_FIELD_ARRIVALTIME_SUFFIX));
        tableInfo.setTravelDateField(properties.getProperty(travelType + Constants.TABLE_FIELD_TRAVELDATE_SUFFIX));
        return tableInfo;
    }

}
