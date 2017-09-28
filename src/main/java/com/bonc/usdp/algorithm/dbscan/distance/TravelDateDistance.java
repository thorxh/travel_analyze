package com.bonc.usdp.algorithm.dbscan.distance;

import com.bonc.usdp.algorithm.dbscan.DistanceMetric;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.system.Constants;

/**
 * created on 2017/9/28
 *
 * @author liyanjun@bonc.com.cn
 */
public class TravelDateDistance implements DistanceMetric<TravelCharacter> {

    private int distance = Integer.MIN_VALUE;

    @Override
    public double calculateDistance(TravelCharacter val1, TravelCharacter val2) {
        if (distance == Integer.MIN_VALUE) {
            distance = cal(val1, val2);
        }
        return distance * Config.SYSTEM_RATE_TRAVELDATE;
    }

    @Override
    public boolean check(TravelCharacter val1, TravelCharacter val2) {
        distance = cal(val1, val2);
        return distance <= Config.SYSTEM_PARAM_MAXDAYGAP;
    }

    private int cal(TravelCharacter val1, TravelCharacter val2) {
        return Math.abs((int) (
                (val1.getTravelDate().getTime() - val2.getTravelDate().getTime()) / Constants.ONE_DAY_MILLISECOND));
    }

}
