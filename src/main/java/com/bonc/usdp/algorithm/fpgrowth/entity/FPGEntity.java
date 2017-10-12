package com.bonc.usdp.algorithm.fpgrowth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created on 2017/10/9
 *
 * @author liyanjun@bonc.com.cn
 */
@Getter
@Setter
@ToString
public class FPGEntity {

    /**
     * 数据集
     */
    private List<List<String>> inputValues;
    /**
     * 频繁项及出现次数
     */
    private Map<String, Integer> elementMap = new HashMap<>();
    /**
     * 频繁项节点记录
     */
    private Map<String, List<TreeNode>> nodeLinkMap = new HashMap<>();
    /**
     * 最小支持度
     */
    private int minSup = 3;
    /**
     * FPTree 根节点
     */
    private TreeNode rootNode = new TreeNode();

    public FPGEntity(List<List<String>> inputValues, int minSup) {
        this.inputValues = inputValues;
        this.minSup = minSup;
    }
}
