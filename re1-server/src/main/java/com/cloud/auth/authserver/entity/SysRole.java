package com.cloud.auth.authserver.entity;

import com.cloud.common.simplequery.IntervalEntity;
import com.cloud.common.webcomm.PageEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * SysRole 实体类
 * @author lijun
 */
public class SysRole extends PageEntity implements Serializable {
	/**
	* field comment: 主键 
	*/
	private Long srId;

	/**
	* field comment: 角色名称 
	*/
	private String roleName;

	/**
	* field comment: 角色名称 
	*/
	private transient Boolean roleNameEqual = Boolean.FALSE;

	/**
	* field comment: 【角色类别】1、公用角色，2、部门专属角色，3：公司专属角色 
	*/
	private Byte roleCategory;

	/**
	* field comment: 【角色类型】1、系统管理员，2、部门管理员，3：公司管理员，4、普通角色 
	*/
	private Byte roleType;

	/**
	* field comment: 角色描述 
	*/
	private String description;

	/**
	* field comment: 角色描述 
	*/
	private transient Boolean descriptionEqual = Boolean.FALSE;

	/**
	* field comment: 记录创建时间 
	*/
	private Date createTime;

	/**
	* field comment: 记录创建时间 
	*/
	private transient Date createTimeStart;

	/**
	* field comment: 记录创建时间 
	*/
	private transient Date createTimeEnd;

	/**
	* field comment: 【记录状态】0：不可用，1：可用 
	*/
	private Byte status;

	/**
	* field comment: 记录更新时间 
	*/
	private Date statusTime;

	/**
	* field comment: 记录更新时间 
	*/
	private transient Date statusTimeStart;

	/**
	* field comment: 记录更新时间 
	*/
	private transient Date statusTimeEnd;

	/**
	* field comment: order by 
	*/
	private transient String orderByClause;

	/**
	* field comment: and xxx in...列表 
	*/
	private transient List<IntervalEntity> inSql;

	/**
	* field comment: and xxx not in 列表 
	*/
	private transient List<IntervalEntity> notInSql;


	/**
	 * field comment: 初始化inSql和notInSql
	 */
	{
		inSql = new ArrayList<>();
		notInSql = new ArrayList<>();
	}

	public static final transient String PROP_SR_ID = "srId";
	public static final transient String TABLE_SR_ID = "sr_id";
	public static final transient String PROP_ROLE_NAME = "roleName";
	public static final transient String TABLE_ROLE_NAME = "role_name";
	public static final transient String PROP_ROLE_CATEGORY = "roleCategory";
	public static final transient String TABLE_ROLE_CATEGORY = "role_category";
	public static final transient String PROP_ROLE_TYPE = "roleType";
	public static final transient String TABLE_ROLE_TYPE = "role_type";
	public static final transient String PROP_DESCRIPTION = "description";
	public static final transient String TABLE_DESCRIPTION = "description";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getSrId() { return srId; }

	public void setSrId(Long srId) { this.srId = srId; }

	public String getRoleName() { return roleName; }

	public void setRoleName(String roleName) { this.roleName = roleName; }

	public Boolean getRoleNameEqual() { return roleNameEqual; }

	public void setRoleNameEqual(Boolean roleNameEqual) { this.roleNameEqual = roleNameEqual; }

	public Byte getRoleCategory() { return roleCategory; }

	public void setRoleCategory(Byte roleCategory) { this.roleCategory = roleCategory; }

	public Byte getRoleType() { return roleType; }

	public void setRoleType(Byte roleType) { this.roleType = roleType; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public Boolean getDescriptionEqual() { return descriptionEqual; }

	public void setDescriptionEqual(Boolean descriptionEqual) { this.descriptionEqual = descriptionEqual; }

	public Date getCreateTime() { return createTime; }

	public void setCreateTime(Date createTime) { this.createTime = createTime; }

	public Date getCreateTimeStart() { return createTimeStart; }

	public void setCreateTimeStart(Date createTimeStart) { this.createTimeStart = createTimeStart; }

	public Date getCreateTimeEnd() { return createTimeEnd; }

	public void setCreateTimeEnd(Date createTimeEnd) { this.createTimeEnd = createTimeEnd; }

	public Byte getStatus() { return status; }

	public void setStatus(Byte status) { this.status = status; }

	public Date getStatusTime() { return statusTime; }

	public void setStatusTime(Date statusTime) { this.statusTime = statusTime; }

	public Date getStatusTimeStart() { return statusTimeStart; }

	public void setStatusTimeStart(Date statusTimeStart) { this.statusTimeStart = statusTimeStart; }

	public Date getStatusTimeEnd() { return statusTimeEnd; }

	public void setStatusTimeEnd(Date statusTimeEnd) { this.statusTimeEnd = statusTimeEnd; }

	public String getOrderByClause() { return orderByClause; }

	public void setOrderByClause(String orderByClause) { this.orderByClause = orderByClause; }

	public List<IntervalEntity> getInSql() { return inSql; }

	public void setInSql(List<IntervalEntity> inSql) { this.inSql = inSql; }

	public List<IntervalEntity> getNotInSql() { return notInSql; }

	public void setNotInSql(List<IntervalEntity> notInSql) { this.notInSql = notInSql; }


}

