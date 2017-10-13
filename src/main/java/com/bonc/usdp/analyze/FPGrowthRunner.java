package com.bonc.usdp.analyze;

import com.bonc.usdp.algorithm.fpgrowth.FPGrowth;
import com.bonc.usdp.algorithm.fpgrowth.api.impl.FileResultProcessor;
import com.bonc.usdp.algorithm.fpgrowth.entity.FreqPattern;
import com.bonc.usdp.algorithm.fpgrowth.entity.FreqPatternResult;
import com.bonc.usdp.algorithm.fpgrowth.entity.TreeNode;
import com.bonc.usdp.util.Elapse;
import com.bonc.usdp.util.FileUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * created on 2017/10/11
 *
 * @author liyanjun@bonc.com.cn
 */
public class FPGrowthRunner {

    private List<List<String>> rawData;

    private int minSup;

    public FPGrowthRunner(List<List<String>> rawData, int minSup) {
        this.rawData = rawData;
        this.minSup = minSup;
    }

    public void run() {
        System.out.println("start mine (FPGrowth) ...");
        Elapse elapse = new Elapse();
        elapse.start();
        FPGrowth fpGrowth = new FPGrowth();
        List<String> freqlist = new LinkedList<>();
        String outPath = "G:\\WorkSpace\\Idea\\travel-analyze\\result" + File.separator + "out.txt";
        FileUtil.deleteFileOrDir(outPath);
        FreqPatternResult freqPatternResult = new FreqPatternResult(
                new FileResultProcessor(outPath)
        );
        fpGrowth.mine(rawData, minSup, freqlist, freqPatternResult);
        freqPatternResult.flush();
        elapse.stop();
        System.out.println("mine done (FPGrowth)");
        System.out.println("total time " + elapse.get());
    }

}
