package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.dbscan.Clusterer;
import com.bonc.usdp.algorithm.dbscan.DistanceMetric;
import com.bonc.usdp.algorithm.dbscan.distance.*;
import com.bonc.usdp.entity.TableInfo;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.util.FileUtil;
import com.bonc.usdp.util.PathUtil;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created on 2017/9/21
 *
 * @author liyanjun@bonc.com.cn
 */
public class Runner {

    public void run() {
        List<TravelCharacter> characterAttributeList = new LinkedList<>();

        for (TableInfo tableInfo : Config.tableInfos) {
            characterAttributeList.addAll(new DataGather(tableInfo).gatherData());
        }

        // no data
        if (characterAttributeList.isEmpty()) {
            return;
        }

        long start = System.currentTimeMillis();

        Clusterer<TravelCharacter> clusterer = new Clusterer<>(
                characterAttributeList,
                Config.SYSTEM_PARAM_MINCLUSTER,
                Config.SYSTEM_PARAM_MAXDISTANCE,
                getDistanceMetrics());

        List<List<TravelCharacter>> results  = clusterer.performClustering();

        long end = System.currentTimeMillis();

        FileUtil.cleanDir(PathUtil.getOutPath());
        for (int i = 0; i < results.size(); i++) {
            FileUtil.writeList(PathUtil.getOutPath() + File.separator + i + ".txt", results.get(i));
        }

        long time = end - start;
        int hour = (int) (time / (1000 * 60 * 60));
        int minute = (int) (time / (1000 * 60));
        int second = (int) (time / 1000);
        Map<String, String> statisticsMap = new HashMap<>();
        statisticsMap.put("record size", String.valueOf(characterAttributeList.size()));
        statisticsMap.put("total time", String.format("%sh %sm %ss", hour, minute, second));
        FileUtil.writeMap(PathUtil.getOutPath() + File.separator + "statistics.txt", statisticsMap);
    }

    private List<DistanceMetric<TravelCharacter>> getDistanceMetrics() {
        String characters = Config.SYSTEM_PARAM_CHARACTER_SWITCH;
        List<DistanceMetric<TravelCharacter>> distanceMetricList = new LinkedList<>();
        for (String character : characters.split(",")) {
            if (character != null && !character.isEmpty()) {
                if("travel_date".equals(character.trim())) {
                    distanceMetricList.add(new TravelDateDistance());
                } else if("travel_type".equals(character.trim())) {
                    distanceMetricList.add(new TravelTypeDistance());
                } else if("company_name".equals(character.trim())) {
                    distanceMetricList.add(new CompanyNameDistance());
                } else if("traffic_number".equals(character.trim())) {
                    distanceMetricList.add(new TrafficNumberDistance());
                } else if("departure".equals(character.trim())) {
                    distanceMetricList.add(new DepartureDistance());
                } else if("destination".equals(character.trim())) {
                    distanceMetricList.add(new DestinationDistance());
                } else if("departure_time".equals(character.trim())) {
                    distanceMetricList.add(new DepartureTimeDistance());
                } else if("arrival_time".equals(character.trim())) {
                    distanceMetricList.add(new ArrivalTimeDistance());
                }
            }
        }
        return distanceMetricList;
    }

}
