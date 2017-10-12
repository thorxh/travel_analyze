package com.bonc.usdp;

import com.bonc.usdp.algorithm.fpgrowth.entity.FreqPattern;
import com.bonc.usdp.analyze.ClustererRunner;
import com.bonc.usdp.analyze.FPGpgrowthRunner;
import com.bonc.usdp.entity.TravelCharacter;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.util.FileUtil;
import com.bonc.usdp.util.PathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

//        List<List<TravelCharacter>> clustererResult = new ClustererRunner().run();
//        List<List<String>> rawData = new ArrayList<>();
//        // 构建数据挖掘输入
//        clustererResult.forEach(travelCharacters -> rawData.add(
//                travelCharacters.stream().map(TravelCharacter::getTravellerId).collect(Collectors.toList()))
//        );

        List<String> values = FileUtil.read("G:\\WorkSpace\\Idea\\travel-analyze\\result\\id_groups.txt");
        List<List<String>> rawData = new LinkedList<>();
        for (String value : values) {
            if (value == null) {
                continue;
            }
            List<String> stringList = new LinkedList<>();
            stringList.addAll(Arrays.asList(value.split(" ")));
            rawData.add(stringList);
        }

        List<FreqPattern> mineResult = new FPGpgrowthRunner(rawData, Config.SYSTEM_PARAM_MIN_SUP).run();
        mineResult.removeIf(result -> result.getPatternList().size() < Config.SYSTEM_PARAM_MIN_PARTNER_NUM);

        List<String> outList = new LinkedList<>();
        mineResult.forEach(itemList -> {
            itemList.getPatternList().sort(String::compareTo);
            outList.add(itemList.getFrequency() + " " + String.join(" ", itemList.getPatternList()));
        });
        outList.sort(String::compareTo);
        FileUtil.writeList("G:\\WorkSpace\\Idea\\travel-analyze\\result" + File.separator + "out.txt", outList);

    }

}
