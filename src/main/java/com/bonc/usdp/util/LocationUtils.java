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
        Area areaOne = getAreaByCounty(placeOne);
        Area areaTwo = getAreaByCounty(placeTwo);
        if (areaOne == null || areaTwo == null) {
            System.out.println(areaOne == null ? placeOne : placeTwo + " is not in the area_position.dic");
            return Double.MAX_VALUE;
        }
        return getDistance(areaOne.getLatitude(), areaOne.getLongitude(), areaTwo.getLatitude(), areaTwo.getLongitude());
    }

    /**
     * 角度弧度计算
     */
    private static double getRadian(double degree) {
        return degree * Math.PI / 180.0;
    }

    /**
     * 依据经纬度计算两点之间的距离(单位: 米)
     * @param lat1 1点的纬度
     * @param lng1 1点的经度
     * @param lat2 2点的纬度
     * @param lng2 2点的经度
     */
    private static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = getRadian(lat1);
        double radLat2 = getRadian(lat2);
        double a = radLat1 - radLat2;
        double b = getRadian(lng1) - getRadian(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        double EARTH_RADIUS = 6378.137;
        s = s * EARTH_RADIUS;
        return s * 1000;
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
                Area area = convertToArea(bufferedSource.readUtf8Line());
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

    private static Area getAreaByCounty(String county) {
        if (!county.contains("市") && !county.contains("区") && !county.contains("县")) {
            Area area = areaMap.get(county + "市");
            if (area != null) {
                return area;
            }
            area = areaMap.get(county + "区");
            if (area != null) {
                return area;
            }
            area = areaMap.get(county + "县");
            return area;
        }
        return areaMap.get(county);
    }

}
