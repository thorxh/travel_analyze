package com.bonc.usdp.entity;

import com.bonc.usdp.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TravelCharacter extends CharacterHelper {

    private String travellerName;
    private String travellerId;
    private Date travelDate;
    private String travelType;
    private String companyName;
    private String trafficNumber;
    private String departure;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;

    @Override
    public String toString() {
        return travellerName + " " +
                travellerId + " " +
                TimeUtil.formatDate(travelDate) + " " +
                travelType + " " +
                companyName + " " +
                trafficNumber + " " +
                departure + " " +
                destination + " " +
                TimeUtil.formatTime(departureTime) + " " +
                TimeUtil.formatTime(arrivalTime);
    }

}
