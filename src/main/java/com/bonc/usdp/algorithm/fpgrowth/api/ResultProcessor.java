package com.bonc.usdp.algorithm.fpgrowth.api;

import com.bonc.usdp.algorithm.fpgrowth.entity.FreqPattern;

import java.util.List;

/**
 * created on 2017/10/12
 *
 * @author liyanjun@bonc.com.cn
 */
public interface ResultProcessor {

    void process(List<FreqPattern> freqPatterns);

}
