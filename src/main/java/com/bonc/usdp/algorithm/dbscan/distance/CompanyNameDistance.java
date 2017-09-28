package com.bonc.usdp.algorithm.dbscan.distance;

import com.bonc.usdp.algorithm.dbscan.DistanceMetric;
import com.bonc.usdp.entity.TravelCharacter;

/**
 * created on 2017/9/28
 *
 * @author liyanjun@bonc.com.cn
 */
public class CompanyNameDistance implements DistanceMetric<TravelCharacter> {

    @Override
    public double calculateDistance(TravelCharacter val1, TravelCharacter val2) {
        return 0;
    }

    @Override
    public boolean check(TravelCharacter val1, TravelCharacter val2) {
        return false;
    }

}
