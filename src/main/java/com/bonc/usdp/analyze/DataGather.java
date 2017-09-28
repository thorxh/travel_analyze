package com.bonc.usdp.analyze;

import com.bonc.usdp.entity.TableInfo;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.util.DbUtil;
import com.bonc.usdp.util.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * created on 2017/9/21
 *
 * @author liyanjun@bonc.com.cn
 */
class DataGather {

    private TableInfo tableInfo;

    private List<String> suffixList = new LinkedList<String>() {
        {
            add("市");
            add("县");
            add("区");
        }
    };

    DataGather(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    List<TravelCharacter> gatherData() {

        StringBuilder querySQL = new StringBuilder();

        querySQL.append("SELECT ");
        for (String field : getFields()) {
            querySQL.append(field).append(", ");
        }
        querySQL.deleteCharAt(querySQL.length() - 2);
        querySQL.append("FROM ").append(tableInfo.getTableName());
        if (Config.SYSTEM_PARAM_RECORDLIMIT != -1) {
            querySQL.append(" LIMIT 0, ").append(Config.SYSTEM_PARAM_RECORDLIMIT);
        }

        Connection conn = DbUtil.getConn(
                tableInfo.getDbUrl(),
                tableInfo.getDbDriver(),
                tableInfo.getDbUserName(),
                tableInfo.getDbPassword());

        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<TravelCharacter> travelCharacterList = new LinkedList<>();
        try {
            stat = conn.prepareStatement(querySQL.toString());
            resultSet = stat.executeQuery();
            while (resultSet.next()) {
                TravelCharacter travelCharacter = new TravelCharacter();
                travelCharacter.setTravellerName(resultSet.getString(tableInfo.getTravellerNameField()));
                travelCharacter.setTravellerId(resultSet.getString(tableInfo.getTravellerIdField()));
                travelCharacter.setTrafficNumber(resultSet.getString(tableInfo.getTrafficNumberField()));
                travelCharacter.setCompanyName(resultSet.getString(tableInfo.getCompanyNameField()));
                travelCharacter.setTravelDate(TimeUtil.parseDate(resultSet.getString(tableInfo.getTravelDateField())));
                travelCharacter.setDeparture(cityNameNormalize(resultSet.getString(tableInfo.getDepartureField())));
                travelCharacter.setDestination(cityNameNormalize(resultSet.getString(tableInfo.getDestinationField())));
                travelCharacter.setDepartureTime(TimeUtil.parseTime(resultSet.getString(tableInfo.getDepartureTimeField())));
                travelCharacter.setArrivalTime(TimeUtil.parseTime(resultSet.getString(tableInfo.getArrivalTimeField())));
                travelCharacterList.add(travelCharacter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn, stat, resultSet);
        }

        return travelCharacterList;
    }

    private List<String> getFields() {
        List<String> fieldList = new LinkedList<>();
        fieldList.add(tableInfo.getTravellerNameField());
        fieldList.add(tableInfo.getTravellerIdField());
        fieldList.add(tableInfo.getCompanyNameField());
        fieldList.add(tableInfo.getTrafficNumberField());
        fieldList.add(tableInfo.getDepartureField());
        fieldList.add(tableInfo.getDestinationField());
        fieldList.add(tableInfo.getDepartureTimeField());
        fieldList.add(tableInfo.getArrivalTimeField());
        fieldList.add(tableInfo.getTravelDateField());
        return fieldList;
    }

    private String cityNameNormalize(String name) {
        if (name == null || name.length() <= 2) {
            return name;
        }
        for (String suffix : suffixList) {
            if (name.contains(suffix)) {
                return name.substring(0, name.length() - suffix.length());
            }
        }
        return name;
    }

}
