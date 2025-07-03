package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("sys_user")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.INPUT)
	private String userId;
	private String username;
	private String password;
	private String email;
	private String mobile;
	private Integer status;
	private String createUserId;
	private Date createTime;
	private String name;
	private String photo;

	@TableField(exist=false)
	private List<Long> roleIdList;
}
