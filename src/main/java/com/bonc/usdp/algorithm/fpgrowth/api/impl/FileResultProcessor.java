package com.bonc.usdp.algorithm.fpgrowth.api.impl;

import com.bonc.usdp.algorithm.fpgrowth.api.ResultProcessor;
import com.bonc.usdp.algorithm.fpgrowth.entity.FreqPattern;
import com.bonc.usdp.analyze.FPGrowthRunner;
import com.bonc.usdp.system.Config;
import com.bonc.usdp.util.FileUtil;

import javax.sql.rowset.serial.SerialRef;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * created on 2017/10/12
 *
 * @author liyanjun@bonc.com.cn
 */
public class FileResultProcessor implements ResultProcessor {

    private String filePath;

    public FileResultProcessor(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void process(List<FreqPattern> freqPatterns) {
        freqPatterns.removeIf(result -> result.getPatternList().size() < Config.SYSTEM_PARAM_MIN_PARTNER_NUM);
        List<String> outList = new LinkedList<>();
        freqPatterns.forEach(itemList -> {
            itemList.getPatternList().sort(String::compareTo);
            outList.add(itemList.getFrequency() + " " + String.join(" ", itemList.getPatternList()));
        });
        outList.sort(String::compareTo);

        FileUtil.appendList(filePath, outList);
    }

}
