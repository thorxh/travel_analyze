package com.bonc.usdp.util;

import java.io.*;
import java.util.*;

/**
 * @author wanghui
 */
public class DistanceUtil {

    private static Map<String, Double> cityDistanceMap = DistanceUtil.readCitiesDistance(
            PathUtil.getConfigPath() + File.separator + "city-distance.dic");

    public static double getDistance(String placeOne, String placeTwo) {
        return cityDistanceMap.get(placeOne + "|" + placeTwo);
    }

    private static Map<String, Double> readCitiesDistance(String inputPath) {
        Map<String, Double> distancesMap = new HashMap<>();
        List<String> citiesList = new ArrayList<>();
        try (BufferedReader fp = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8"))) {
            //读取第一行
            String firstLine = fp.readLine();
            StringTokenizer fst = new StringTokenizer(firstLine, "	");
            int fm = fst.countTokens();
            for (int i = 0; i < fm; i++) {
                String temp = fst.nextToken();
                if (!temp.isEmpty()) {
                    citiesList.add(temp);
                }
            }
            //读取第二行后面的
            while (true) {
                String line = fp.readLine();
                if (line == null)
                    break;
                StringTokenizer st = new StringTokenizer(line, "	");
                String cityName = st.nextToken();
                for (String aCitiesList : citiesList) {
                    String temp2 = st.nextToken();
                    distancesMap.put(aCitiesList + "|" + cityName, Double.valueOf(temp2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return distancesMap;
    }
}
