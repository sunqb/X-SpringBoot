package com.suke.czx.modules.sys.vo;

import com.suke.czx.common.utils.TreeNode;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "菜单管理VO")
public class SysMenuNewVO implements Serializable, TreeNode<SysMenuNewVO> {

    private Long menuId;
    private Long parentId;
    private String path;
    private String name;
    private String menuType;
    private String component;
    private String redirect;
    private String title;
    private int orderSort;
    private boolean isLink;

    private RouterMetaVO meta;

    private List<SysMenuNewVO> children;

    @Override
    public Long getId() {
        return this.menuId;
    }

    @Override
    public Long getParentId() {
        return this.parentId;
    }

    @Override
    public void setChildren(List<SysMenuNewVO> children) {
        this.children = children;
    }

    @Override
    public List<SysMenuNewVO> getChildren() {
        return this.children;
    }
}