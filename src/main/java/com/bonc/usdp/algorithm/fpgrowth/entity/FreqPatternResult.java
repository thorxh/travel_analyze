package com.bonc.usdp.algorithm.fpgrowth.entity;

import com.bonc.usdp.algorithm.fpgrowth.api.ResultProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * created on 2017/10/12
 *     此类用于存放频繁项集，当频繁项集数量达到一定值时会将这些结果保存起来(写数据库或者写文件)，这样做的目的是释放内存
 * @author liyanjun@bonc.com.cn
 */
public class FreqPatternResult {

    /**
     * 记录频繁项集中项的数量
     */
    private long itemLength;

    private List<FreqPattern> freqPatterns = new LinkedList<>();

    private ResultProcessor resultProcessor;

    public FreqPatternResult(ResultProcessor resultProcessor) {
        this.resultProcessor = resultProcessor;
    }

    public void addFreqPattern(FreqPattern freqPattern) {
        freqPatterns.add(freqPattern);
        itemLength += freqPattern.getPatternList().size();
        long threshold = 50000;
        if (itemLength > threshold || threshold - itemLength < 1000) {
            flush();
        }
    }

    public void flush() {
        resultProcessor.process(freqPatterns);
        freqPatterns.clear();
    }

}
