package com.bonc.usdp.algorithm.dbscan.distance;

import com.bonc.usdp.algorithm.dbscan.DistanceMetric;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.util.LocationUtils;

/**
 * created on 2017/9/28
 *
 * @author liyanjun@bonc.com.cn
 */
public class DepartureDistance implements DistanceMetric<TravelCharacter> {

    private double distance = Integer.MIN_VALUE;

    @Override
    public double calculateDistance(TravelCharacter val1, TravelCharacter val2) {
        if (distance == Integer.MIN_VALUE) {
            distance = cal(val1, val2);
        }
        return distance * Config.SYSTEM_RATE_DEPARTURE / 100;
    }

    @Override
    public boolean check(TravelCharacter val1, TravelCharacter val2) {
        distance = cal(val1, val2);
        return distance <= Config.SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE;
    }

    private double cal(TravelCharacter val1, TravelCharacter val2) {
        return LocationUtils.getDistance(val1.getDeparture(), val2.getDeparture());
    }

}
