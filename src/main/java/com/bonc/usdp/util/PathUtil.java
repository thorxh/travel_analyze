package com.bonc.usdp.util;

/**
 * <br>Created by liyanjun@bonc.com.cn on 2017/9/21.<br><br>
 */
public class PathUtil {

    private static String configPath;

    private static String outPath;

    public static String getConfigPath() {
        return PathUtil.configPath != null ? PathUtil.configPath : PathUtil.class.getResource("/").getPath();
    }

    public static void setConfigPath(String configPath) {
        PathUtil.configPath = configPath;
    }

    public static void setOutPath(String outPath) {
        PathUtil.outPath = outPath;
    }

    public static String getOutPath() {
        return outPath;
    }
}
