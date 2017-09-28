package com.bonc.usdp.util;

import com.bonc.usdp.entity.Area;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.bonc.usdp.system.Constants;

/**
 * created on 2017/9/26
 *
 * @author liyanjun@bonc.com.cn
 */
public class LocationUtils {

    private static Map<String, Area> areaMap = new HashMap<>();

    static {
        init();
    }

    public static double getDistance(String placeOne, String placeTwo) {
        Area areaOne = areaMap.get(placeOne);
        Area areaTwo = areaMap.get(placeTwo);
        if (areaOne == null || areaTwo == null) {
            System.out.println(areaOne == null ? placeOne : placeTwo + " is not in the area_position.dic");
            return Double.MAX_VALUE;
        }
        return getDistance(areaOne.getLatitude(), areaOne.getLongitude(), areaTwo.getLatitude(), areaTwo.getLongitude());
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

    private static void init() {
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

}
