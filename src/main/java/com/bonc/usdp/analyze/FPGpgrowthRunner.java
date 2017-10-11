package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.fpgrowth.FPGpgrowth;

import java.util.LinkedList;
import java.util.List;

/**
 * created on 2017/10/11
 *
 * @author liyanjun@bonc.com.cn
 */
public class FPGpgrowthRunner {

    private List<List<String>> rawData;

    private int minSup;

    public FPGpgrowthRunner(List<List<String>> rawData, int minSup) {
        this.rawData = rawData;
        this.minSup = minSup;
    }

    public List<List<String>> run() {
        FPGpgrowth fpGpgrowth = new FPGpgrowth();
        List<String> freqlist = new LinkedList<>();
        List<List<String>> freqItemList = new LinkedList<>();
        fpGpgrowth.mineTree(rawData, minSup, freqlist, freqItemList, 0);
        return freqItemList;
    }

}
