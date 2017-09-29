package com.bonc.usdp;

import com.bonc.usdp.analyze.Runner;
import com.bonc.usdp.util.PathUtil;

/**
 * created on 2017/9/21
 *
 * @author liyanjun@bonc.com.cn
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("output path is not setted");
        }
        PathUtil.setOutPath(args[0]);
        if (args.length > 1) {
            String configPath = args[1];
            if (configPath != null && !configPath.isEmpty()) {
                PathUtil.setConfigPath(configPath);
            }
        }

        new Runner().run();
    }

}
