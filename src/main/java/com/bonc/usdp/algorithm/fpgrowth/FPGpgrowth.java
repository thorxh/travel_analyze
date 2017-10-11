package com.bonc.usdp.algorithm.fpgrowth;

import com.bonc.usdp.algorithm.fpgrowth.entity.FPGEntity;
import com.bonc.usdp.algorithm.fpgrowth.entity.TreeNode;
import com.bonc.usdp.util.FileUtil;

import java.io.File;
import java.util.*;

/**
 * created on 2017/9/30
 *
 * @author liyanjun@bonc.com.cn
 */
public class FPGpgrowth {

    private FPGEntity createTree(List<List<String>> inputValues, int minSup) {
        FPGEntity fpgEntity = new FPGEntity(inputValues, minSup);

        countElement(fpgEntity.getInputValues(), fpgEntity.getElementMap(), fpgEntity.getMinSup(), fpgEntity.getExcludeKeys());
        filterAndSortItemSet(fpgEntity.getInputValues(), fpgEntity.getElementMap(), fpgEntity.getExcludeKeys());
        buildFPTree(fpgEntity.getInputValues(), fpgEntity.getRootNode(), fpgEntity.getNodeLinkMap());

        fpgEntity.setInputValues(null);
        return fpgEntity;
    }

    /**
     * 统计每个项出现次数
     */
    private void countElement(List<List<String>> inputValues, Map<String, Integer> elementMap, int minSup, List<String> excludeKeys) {
        inputValues.forEach(input -> input.forEach(
                value ->
                        elementMap.put(value, Optional.ofNullable(elementMap.get(value)).orElse(0) + 1)
        ));

        // 过滤掉小于阈值的项
        elementMap.forEach((key, value) -> {
            if (value < minSup) {
                excludeKeys.add(key);
            }
        });
        excludeKeys.forEach(elementMap::remove);
    }

    private void filterAndSortItemSet(List<List<String>> inputValues, Map<String, Integer> elementMap, List<String> excludeKeys) {
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
     * 降序排序
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

    private void buildFPTree(List<List<String>> inputValues, TreeNode rootNode, Map<String, List<TreeNode>> nodeLinkMap) {
        for (List<String> inputValue : inputValues) {
            TreeNode node = rootNode;
            for (String value : inputValue) {
                if (value != null) {
                    node = grow(node, value);
                    nodeLinkMap.computeIfAbsent(value, k -> new LinkedList<>()).add(node);
                }
            }
        }
    }

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

    private List<List<String>> getConditionalPattern(String item, Map<String, List<TreeNode>> nodeLinkMap, TreeNode rootNode) {
        List<TreeNode> nodeLinks = nodeLinkMap.get(item);
        if (nodeLinks.isEmpty()) {
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

    public void mineTree(List<List<String>> dataSet, int minSup, List<String> preFix, List<List<String> > freqItemList, int f) {
        int flag = f;
        FPGEntity fpgEntity = createTree(dataSet, minSup);
        if (fpgEntity.getElementMap().isEmpty()) {
            return;
        }
        List<String> elements = sortByOccurTime(fpgEntity.getElementMap());
        for (String element : elements) {
            List<String> newFreqList = new LinkedList<>(preFix);
            newFreqList.add(element);
            freqItemList.add(newFreqList);
            List<List<String>> condPattern = getConditionalPattern(element, fpgEntity.getNodeLinkMap(), fpgEntity.getRootNode());
            if (condPattern.isEmpty()) {
                continue;
            }
//            if (flag == 0) {
//                System.err.println(flag + " " + element + " - " + condPattern);
//            }
            FPGEntity fpg = createTree(condPattern, minSup);
            if (!fpg.getElementMap().isEmpty()) {
                mineTree(condPattern, minSup, newFreqList, freqItemList, flag + 1);
            }
        }
//        if (flag == 0) {
//            fpgEntity.getRootNode().disp(0);
//        }
    }

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

}
