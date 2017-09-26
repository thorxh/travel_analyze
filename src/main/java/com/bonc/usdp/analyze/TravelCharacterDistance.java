package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.dbscan.DistanceMetric;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.system.Constants;
import com.bonc.usdp.util.DistanceUtil;

public class TravelCharacterDistance implements DistanceMetric<TravelCharacter> {

    @Override
    public double calculateDistance(TravelCharacter characterAttributeA, TravelCharacter characterAttributeB) {
        int dayGap = Math.abs((int) (
                (characterAttributeA.getTravelDate().getTime() - characterAttributeB.getTravelDate().getTime()) /
                        (1000 * 3600 * 24)));

        if (dayGap > Config.SYSTEM_PARAM_MAXDAYGAP) {
            return Double.MAX_VALUE;
        }

        double desDis = DistanceUtil.getDistance(
                characterAttributeA.getDestination(), characterAttributeB.getDestination());

        if (dayGap == 0 && desDis == 0) {
            return 0;
        }

        double originDis = DistanceUtil.getDistance(
                characterAttributeA.getDeparture(), characterAttributeB.getDeparture());

        if (originDis > Config.SYSTEM_PARAM_MAXCITYDISTANCE_DEPARTURE
                || desDis > Config.SYSTEM_PARAM_MAXCITYDISTANCE_DESTINATION) {
            return Double.MAX_VALUE;
        }

        return Constants.TRAVEL_DATE_RATE * dayGap + Constants.TRAVEL_DESTINATION_RATE * desDis / 100;
    }
}