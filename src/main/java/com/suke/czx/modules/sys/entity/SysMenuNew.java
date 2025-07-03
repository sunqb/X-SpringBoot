package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.suke.czx.common.utils.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("sys_menu_new")
@ApiModel(value = "菜单管理")
public class SysMenuNew implements Serializable, TreeNode<SysMenuNew> {

    @TableId(value = "menu_id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    private Long parentId;

    private String path;
    private String name;
    private String component;
    private String redirect;
    private String title;
    private String isLink;
    private boolean isHide;
    private boolean isKeepAlive;
    private boolean isAffix;
    private boolean isIframe;
    private String icon;
    private String roles;
    private int orderSort;
    private boolean disabled;

    @TableField(exist = false)
    private List<SysMenuNew> children;

    @Override
    public Long getId() {
        return this.menuId;
    }

    @Override
    public Long getParentId() {
        return this.parentId;
    }

    @Override
    public void setChildren(List<SysMenuNew> children) {
        this.children = children;
    }

    @Override
    public List<SysMenuNew> getChildren() {
        return this.children;
    }
}