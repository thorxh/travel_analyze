package com.bonc.usdp.algorithm.fpgrowth.entity;

import com.bonc.usdp.algorithm.fpgrowth.FPGrowthExp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * created on 2017/9/29
 *
 * @author liyanjun@bonc.com.cn
 */
@Setter
@Getter
@NoArgsConstructor
public class TreeNode {

    private String value;
    private int count;
    private TreeNode parent;
    private List<TreeNode> children = new LinkedList<>();
    private Map<String, TreeNode> childrenMap = new HashMap<>();

    public TreeNode(String value, int count, TreeNode parent) {
        if (value == null) {
            throw new RuntimeException("null value");
        }
        this.value = value;
        this.count = count;
        this.parent = parent;
    }

    public void addChild(TreeNode child) {
        children.add(child);
        String value = child.getValue();
        if (childrenMap.get(value) != null) {
            throw new FPGrowthExp(String.format("child %s existed, this should not happen, check your code logic", value));
        }
        childrenMap.put(value, child);
    }

    public TreeNode getChild(String value) {
        return childrenMap.get(value);
    }

    public void inc() {
        count++;
    }

    public void disp(int level) {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < level; i++) {
            space.append("  ");
        }
        System.out.println(space.toString() + "" + value + " " + count);
        for (TreeNode child : children) {
            child.disp(level + 1);
        }
    }

}
