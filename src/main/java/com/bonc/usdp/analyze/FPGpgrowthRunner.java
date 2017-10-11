package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.fpgrowth.FPGpgrowth;
import com.bonc.usdp.util.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * created on 2017/10/11
 *
 * @author liyanjun@bonc.com.cn
 */
public class FPGpgrowthRunner {

    public void run() {
        int minSup = 4;
        List<String> values = FileUtil.read("G:\\WorkSpace\\Idea\\travel-analyze\\result\\id_groups.txt");
        List<List<String>> input = new LinkedList<>();
        for (String value : values) {
            if (value == null) {
                continue;
            }
            List<String> stringList = new LinkedList<>();
            stringList.addAll(Arrays.asList(value.split(" ")));
            input.add(stringList);
        }
        FPGpgrowth fpGpgrowth = new FPGpgrowth();
        List<String> freqlist = new LinkedList<>();
        List<List<String>> freqItemList = new LinkedList<>();
        fpGpgrowth.mineTree(input, minSup, freqlist, freqItemList, 0);

        List<String> outList = new LinkedList<>();
        freqItemList.forEach(itemList -> {
            itemList.sort(String::compareTo);
            outList.add(String.join(" ", itemList));
        });
        outList.sort(String::compareTo);
        FileUtil.writeList("G:\\WorkSpace\\Idea\\travel-analyze\\result" + File.separator + "out.txt", outList);
    }

}
