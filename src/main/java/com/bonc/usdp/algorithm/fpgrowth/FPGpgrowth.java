package com.bonc.usdp.algorithm.fpgrowth;

import com.bonc.usdp.algorithm.fpgrowth.entity.FPGEntity;
import com.bonc.usdp.algorithm.fpgrowth.entity.TreeNode;

import java.util.*;

/**
 * created on 2017/9/30 <br>
 *    使用 FPGpgrowth 算法进行频繁项集挖掘
 * @author liyanjun@bonc.com.cn
 */
public class FPGpgrowth {

    /**
     * 数据挖掘
     */
    public void mine(List<List<String>> dataSet, int minSup, List<String> preFix, List<List<String> > freqPatternList) {
        if (dataSet == null || dataSet.isEmpty()) {
            throw new FPGrowthExp("no data");
        }
        FPGEntity fpgEntity = createTree(dataSet, minSup);
        if (fpgEntity.getElementMap().isEmpty()) {
            return;
        }
        List<String> elements = sortByOccurTime(fpgEntity.getElementMap());
        for (String element : elements) {
            List<String> newFreqList = new LinkedList<>(preFix);
            newFreqList.add(element);
            freqPatternList.add(newFreqList);
            List<List<String>> condPattern =
                    getCondPattern(fpgEntity.getNodeLinkMap().get(element), fpgEntity.getRootNode());
            if (condPattern.isEmpty()) {
                continue;
            }
            FPGEntity fpg = createTree(condPattern, minSup);
            if (!fpg.getElementMap().isEmpty()) {
                mine(condPattern, minSup, newFreqList, freqPatternList);
            }
        }
    }

    /**
     * 构建 PFTree
     */
    private FPGEntity createTree(List<List<String>> inputValues, int minSup) {
        FPGEntity fpgEntity = new FPGEntity(inputValues, minSup);
        preProccess(fpgEntity);
        buildFPTree(fpgEntity);
        fpgEntity.setInputValues(null);
        return fpgEntity;
    }

    /**
     * 数据集预处理
     */
    private void preProccess(FPGEntity fpgEntity) {
        List<List<String>> inputValues = fpgEntity.getInputValues();

        // 统计数据集中所有项出现次数
        Map<String, Integer> elementMap = fpgEntity.getElementMap();
        inputValues.forEach(input -> input.forEach(
                value ->
                        elementMap.put(value, Optional.ofNullable(elementMap.get(value)).orElse(0) + 1)
        ));

        // 过滤掉小于阈值的项
        int minSup = fpgEntity.getMinSup();
        List<String> excludeKeys = new LinkedList<>();
        elementMap.forEach((key, value) -> {
            if (value < minSup) {
                excludeKeys.add(key);
            }
        });
        excludeKeys.forEach(elementMap::remove);

        // 去除数据集项集中出现次数小于最小支持的项，并排序剩下的项集
        for (List<String> inputValue : inputValues) {
            inputValue.removeIf(excludeKeys::contains);
            int[] occurTimes = new int[inputValue.size()];
            for (int i = 0; i < inputValue.size(); i++) {
                occurTimes[i] = elementMap.get(inputValue.get(i));
            }
            sort(inputValue, occurTimes);
        }
        inputValues.removeIf(List::isEmpty);
    }

    /**
     * 构建 FPTree
     */
    private void buildFPTree(FPGEntity fpgEntity) {
        TreeNode rootNode = fpgEntity.getRootNode();
        Map<String, List<TreeNode>> nodeLinkMap = fpgEntity.getNodeLinkMap();
        for (List<String> inputValue : fpgEntity.getInputValues()) {
            TreeNode node = rootNode;
            for (String value : inputValue) {
                if (value != null) {
                    node = grow(node, value);
                    nodeLinkMap.computeIfAbsent(value, k -> new LinkedList<>()).add(node);
                }
            }
        }
    }

    /**
     * FPTree 生长
     */
    private TreeNode grow(TreeNode parent, String value) {
        TreeNode node = parent.getChild(value);
        if (node == null) {
            node = new TreeNode(value, 1, parent);
            parent.addChild(node);
        } else {
            node.inc();
        }
        return node;
    }

    /**
     * 抽取条件模式基 <br>
     *    条件模式基是以所查找元素项为结尾的路径集合。
     *    每一条路径其实都是一条前缀路径（prefixpath）。
     *    简而言之，一条前缀路径是介于所查找元素项与树根节点之间的所有内容。
     */
    private List<List<String>> getCondPattern(List<TreeNode> nodeLinks, TreeNode rootNode) {
        if (nodeLinks == null || nodeLinks.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<List<String>> condPatterns = new LinkedList<>();
            for (TreeNode nodeLink : nodeLinks) {
                if (nodeLink != null) {
                    List<String> ascendTree = ascendTree(rootNode, nodeLink);
                    if (!ascendTree.isEmpty()) {
                        condPatterns.add(ascendTree);
                    }
                }
            }
            return condPatterns;
        }
    }

    /**
     * 回溯 FPTree
     */
    private List<String> ascendTree(TreeNode rootNode, TreeNode treeNode) {
        List<String> items = new LinkedList<>();
        TreeNode node = treeNode;
        TreeNode parent;
        while ((parent = node.getParent()) != rootNode) {
            items.add(0, parent.getValue());
            node = parent;
        }
        return items;
    }

    /**
     * 根据项出现次数排序项
     */
    private List<String> sortByOccurTime(Map<String, Integer> elementMap) {
        List<String> elements = new LinkedList<>();
        int[] occurTimes = new int[elementMap.size()];
        int i = 0;
        for (String key : elementMap.keySet()) {
            elements.add(i, key);
            occurTimes[i] = elementMap.get(key);
            i++;
        }
        sort(elements, occurTimes);
        return elements;
    }

    /**
     * 降序排序，出现次数相同时升序排序
     */
    private void sort(List<String> items, int[] occurTimes) {
        int index;
        int temp;
        String tempStr;
        for (int i = 0; i < occurTimes.length - 1; i++) {
            index = i;
            temp = occurTimes[i];
            tempStr = items.get(i);
            for (int j = i + 1; j < occurTimes.length; j++) {
                if (occurTimes[j] > temp) {
                    index = j;
                    temp = occurTimes[j];
                    tempStr = items.get(j);
                } else if (occurTimes[j] == temp) {
                    if (tempStr.compareTo(items.get(j)) > 0) {
                        index = j;
                        temp = occurTimes[j];
                        tempStr = items.get(j);
                    }
                }
            }
            if (index != i) {
                occurTimes[index] = occurTimes[i];
                occurTimes[i] = temp;

                String str = items.get(index);
                items.set(index, items.get(i));
                items.set(i, str);
            }
        }
    }

}
