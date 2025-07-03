package com.suke.czx.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czx
 * @title: TreeUtil
 * @projectName x-springboot
 * @description: 树形结构工具类
 * @date 2024/7/3 19:05
 */
public class TreeUtil {

    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(treeNode.getParentId())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getId().equals(treeNode.getParentId())) {
                if (rootNode.getChildren() == null) {
                    rootNode.setChildren(new ArrayList<>());
                }
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }
}
