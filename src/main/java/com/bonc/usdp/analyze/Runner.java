package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.dbscan.Clusterer;
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

        long start = System.currentTimeMillis();

        Clusterer<TravelCharacter> clusterer = new Clusterer<>(
                characterAttributeList,
                Config.SYSTEM_PARAM_MINCLUSTER,
                Config.SYSTEM_PARAM_MAXDISTANCE,
                new TravelCharacterDistance());

        List<List<TravelCharacter>> results = clusterer.performClustering();

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

}
