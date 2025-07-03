package com.suke.czx.common.utils;

import java.util.List;

/**
 * @author czx
 * @title: TreeNode
 * @projectName x-springboot
 * @description: 树形节点接口
 * @date 2024/7/3 19:00
 */
public interface TreeNode<T> {

    Long getId();

    Long getParentId();

    void setChildren(List<T> children);

    List<T> getChildren();
}
