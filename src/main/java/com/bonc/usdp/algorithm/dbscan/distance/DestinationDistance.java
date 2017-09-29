package com.bonc.usdp.algorithm.dbscan.distance;

import com.bonc.usdp.algorithm.dbscan.DistanceMetric;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.util.DistanceUtils;

/**
 * created on 2017/9/28
 *
 * @author liyanjun@bonc.com.cn
 */
public class DestinationDistance implements DistanceMetric<TravelCharacter> {

    private double distance = Integer.MIN_VALUE;

    @Override
    public double calculateDistance(TravelCharacter val1, TravelCharacter val2) {
        if (distance == Integer.MIN_VALUE) {
            distance = cal(val1, val2);
        }
        return distance * Config.SYSTEM_RATE_DESTINATION / 100;
    }

    @Override
    public boolean check(TravelCharacter val1, TravelCharacter val2) {
        distance = cal(val1, val2);
        return distance <= Config.SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION;
    }

    private double cal(TravelCharacter val1, TravelCharacter val2) {
        return DistanceUtils.getDistance(val1.getDestination(), val2.getDestination());
    }

}
