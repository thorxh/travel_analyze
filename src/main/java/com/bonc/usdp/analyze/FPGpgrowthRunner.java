package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.fpgrowth.FPGpgrowth;
import com.bonc.usdp.algorithm.fpgrowth.entity.FreqPattern;

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

    public List<FreqPattern> run() {
        FPGpgrowth fpGpgrowth = new FPGpgrowth();
        List<String> freqlist = new LinkedList<>();
        List<FreqPattern> freqItemList = new LinkedList<>();
        fpGpgrowth.mine(rawData, minSup, freqlist, freqItemList);
        return freqItemList;
    }

}
