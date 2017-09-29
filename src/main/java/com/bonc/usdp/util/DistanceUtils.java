package com.bonc.usdp.util;

import com.bonc.usdp.entity.Area;
import com.bonc.usdp.system.Constants;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * created on 2017/9/26
 *
 * @author liyanjun@bonc.com.cn
 */
public class DistanceUtils {

    public enum DistanceCalType {
        ByCityDistance, ByCityLocation
    }

    private static DistanceCalType distanceCalType = DistanceCalType.ByCityLocation;

    private static boolean inited;

    private static Map<String, Area> areaMap;

    private static Map<String, Long> distanceMap;

    private static List<String> missAreas = new LinkedList<>();

    public static void setDistanceCalType(DistanceCalType distanceCalType) {
        DistanceUtils.distanceCalType = distanceCalType;
    }

    public static double getDistance(String placeOne, String placeTwo) {
        if (!inited) {
            initDistance();
            inited = true;
        }
        if (DistanceCalType.ByCityLocation.equals(distanceCalType)) {
            return calDisByLoc(placeOne, placeTwo);
        } else {
            return calDisByDis(placeOne, placeTwo);
        }
    }

    private static double calDisByLoc(String placeOne, String placeTwo) {
        Area areaOne = areaMap.get(placeOne);
        Area areaTwo = areaMap.get(placeTwo);
        if (areaOne == null || areaTwo == null) {
            if (!missAreas.contains(areaOne == null ? placeOne : placeTwo)) {
                missAreas.add(areaOne == null ? placeOne : placeTwo);
            }
            return Double.MAX_VALUE;
        }
        return getDistance(areaOne.getLatitude(), areaOne.getLongitude(), areaTwo.getLatitude(), areaTwo.getLongitude());
    }

    private static double calDisByDis(String placeOne, String placeTwo) {
        String place = placeOne.compareTo(placeTwo) >= 0 ? placeTwo + "-" + placeOne : placeOne + "-" + placeTwo;
        Long distance = distanceMap.get(place);
        if (distance == null) {
            if (!missAreas.contains(place)) {
                missAreas.add(place);
            }
            distance = Long.MAX_VALUE;
        }
        return distance;
    }

    /**
     * 依据经纬度计算两点之间的距离(单位: 米)
     * @param lat1 1点的纬度
     * @param lng1 1点的经度
     * @param lat2 2点的纬度
     * @param lng2 2点的经度
     */
    private static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = Constants.DEGREE_UNIT * lat1;
        double radLat2 = Constants.DEGREE_UNIT * lat2;
        double a = radLat1 - radLat2;
        double b = Constants.DEGREE_UNIT * (lng1 - lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        return s * 6378.137 * 1000;
    }

    private static void initDistance() {
        if (DistanceCalType.ByCityLocation.equals(distanceCalType)) {
            areaMap = readLocationDic();
        } else {
            readDistanceDic();
        }
    }

    private static Map<String, Area> readLocationDic() {
        Map<String, Area> areaMap = new HashMap<>();
        String dicPath = PathUtil.getConfigPath() + File.separator + "area_position.dic";
        File dicFile = new File(dicPath);
        if (!dicFile.exists()) {
            throw new RuntimeException("area position dic not found");
        }
        try (Source src = Okio.source(dicFile);
            BufferedSource bufferedSource = Okio.buffer(src)
        ) {
            // 跳过表头
            bufferedSource.readUtf8Line();
            while (!bufferedSource.exhausted()) {
                String areaStr = bufferedSource.readUtf8Line();
                if (areaStr == null || areaStr.isEmpty()) {
                    continue;
                }
                Area area = convertToArea(areaStr);
                areaMap.put(area.getCounty(), area);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return areaMap;
    }

    private static void readDistanceDic() {
        String dicPath = PathUtil.getConfigPath() + File.separator + "area_distance.dic";
        File dicFile = new File(dicPath);
        if (!dicFile.exists() && dicFile.length() == 0) {
            calculateDisByLocation();
        } else {
            distanceMap = readDisDic();
        }
    }

    private static Map<String, Long> readDisDic() {
        Map<String, Long> disMap = new HashMap<>();
        String dicPath = PathUtil.getConfigPath() + File.separator + "area_distance.dic";
        File dicFile = new File(dicPath);
        if (!dicFile.exists()) {
            throw new RuntimeException("area position dic not found");
        }
        try (BufferedSource bufferedSource = Okio.buffer(Okio.source(dicFile))) {
            while (!bufferedSource.exhausted()) {
                String disStr = bufferedSource.readUtf8Line();
                if (disStr == null || disStr.isEmpty()) {
                    continue;
                }
                String[] split = disStr.split(" ");
                disMap.put(split[0], Long.parseLong(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disMap;
    }

    private static Area convertToArea(String areaStr) {
        StringTokenizer st = new StringTokenizer(areaStr, ",");
        Area area = new Area();
        area.setProvince(st.nextToken());
        area.setCity(st.nextToken());
        area.setCounty(st.nextToken());
        area.setLongitude(Double.parseDouble(st.nextToken()));
        area.setLatitude(Double.parseDouble(st.nextToken()));
        return area;
    }

    private static void calculateDisByLocation() {
        distanceMap = new HashMap<>();
        Map<String, Area> areaMap = readLocationDic();
        List<String> distanceList = new LinkedList<>();
        List<Area> areaList = new LinkedList<>(areaMap.values());
        for (int i = 0; i < areaList.size(); i++) {
            for (int j = i; j < areaList.size(); j++) {
                double distance =
                        getDistance(
                                areaList.get(i).getLatitude(),
                                areaList.get(i).getLongitude(),
                                areaList.get(j).getLatitude(),
                                areaList.get(j).getLongitude());
                String placeOne = areaList.get(i).getCounty();
                String placeTwo = areaList.get(j).getCounty();
                String cityKey = placeOne.compareTo(placeTwo) >= 0 ? placeTwo + "-" + placeOne : placeOne + "-" + placeTwo;
                distanceList.add(cityKey + " " + (long) distance);
                distanceMap.put(cityKey, (long) distance);
            }
        }
        writeDic(distanceList);
    }

    private static void writeDic(List<String> distanceList) {
        String outPath = PathUtil.getConfigPath() + File.separator + "area_distance.dic";
        File outFile = new File(outPath);
        try(BufferedSink bufferedSink = Okio.buffer(Okio.sink(outFile))) {
            for (String distance : distanceList) {
                bufferedSink.writeUtf8(distance).writeUtf8("\n");
            }
            bufferedSink.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
