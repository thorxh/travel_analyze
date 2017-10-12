package com.bonc.usdp.algorithm.fpgrowth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created on 2017/10/12
 *
 * @author liyanjun@bonc.com.cn
 */
@Setter
@Getter
@AllArgsConstructor
public class FreqPattern {
    /**
     * 频繁项集列表
     */
    private List<String> patternList;
    /**
     * 出现次数
     */
    private int frequency;

}
