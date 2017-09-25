package com.bonc.usdp.entity;

import lombok.Data;

/**
 * created on 2017/9/21
 *
 * @author liyanjun@bonc.com.cn
 */
@Data
public class TableInfo {

    private String dbUrl;
    private String dbDriver;
    private String dbUserName;
    private String dbPassword;

    private String tableName;

    private String travellerNameField;
    private String travellerIdField;
    private String companyNameField;
    private String trafficNumberField;
    private String departureField;
    private String destinationField;
    private String departureTimeField;
    private String arrivalTimeField;
    private String travelDateField;

}
