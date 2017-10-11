package com.bonc.usdp.algorithm.fpgrowth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

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
    }

    public TreeNode getChild(String value) {
        for (TreeNode child : children) {
            if (child.value.equals(value)) {
                return child;
            }
        }
        return null;
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
