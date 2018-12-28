package com.cloud.auth.authserver.entity;

import java.io.Serializable;
import com.cloud.common.simplequery.IntervalEntity;
import com.cloud.common.webcomm.PageEntity;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.lang.Long;
import java.util.Date;
import java.lang.Byte;
import java.lang.String;


/**
 * SysPermission 实体类
 * @author lijun
 */
public class SysPermission extends PageEntity implements Serializable {
	/**
	* field comment: 主键 
	*/
	private Long spId;

	/**
	* field comment: 权限名称 
	*/
	private String permName;

	/**
	* field comment: 权限名称 
	*/
	private transient Boolean permNameEqual = Boolean.FALSE;

	/**
	* field comment: 权限描述 
	*/
	private String description;

	/**
	* field comment: 权限描述 
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

	public static final transient String PROP_SP_ID = "spId";
	public static final transient String TABLE_SP_ID = "sp_id";
	public static final transient String PROP_PERM_NAME = "permName";
	public static final transient String TABLE_PERM_NAME = "perm_name";
	public static final transient String PROP_DESCRIPTION = "description";
	public static final transient String TABLE_DESCRIPTION = "description";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getSpId() { return spId; }

	public void setSpId(Long spId) { this.spId = spId; }

	public String getPermName() { return permName; }

	public void setPermName(String permName) { this.permName = permName; }

	public Boolean getPermNameEqual() { return permNameEqual; }

	public void setPermNameEqual(Boolean permNameEqual) { this.permNameEqual = permNameEqual; }

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

