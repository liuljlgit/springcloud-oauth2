package com.cloud.auth.authserver.entity;

import java.io.Serializable;
import com.cloud.common.simplequery.IntervalEntity;
import com.cloud.common.webcomm.PageEntity;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.lang.Long;
import java.lang.Integer;
import java.util.Date;
import java.lang.Byte;
import java.lang.String;


/**
 * SysMenu 实体类
 * @author lijun
 */
public class SysMenu extends PageEntity implements Serializable {
	/**
	* field comment: 主键 
	*/
	private Long smId;

	/**
	* field comment: 父级菜单 
	*/
	private Long psmId;

	/**
	* field comment: 菜单名称 
	*/
	private String name;

	/**
	* field comment: 菜单名称 
	*/
	private transient Boolean nameEqual = Boolean.FALSE;

	/**
	* field comment: 链接地址 
	*/
	private String url;

	/**
	* field comment: 链接地址 
	*/
	private transient Boolean urlEqual = Boolean.FALSE;

	/**
	* field comment: 菜单排序 
	*/
	private Integer sort;

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

	public static final transient String PROP_SM_ID = "smId";
	public static final transient String TABLE_SM_ID = "sm_id";
	public static final transient String PROP_PSM_ID = "psmId";
	public static final transient String TABLE_PSM_ID = "psm_id";
	public static final transient String PROP_NAME = "name";
	public static final transient String TABLE_NAME = "name";
	public static final transient String PROP_URL = "url";
	public static final transient String TABLE_URL = "url";
	public static final transient String PROP_SORT = "sort";
	public static final transient String TABLE_SORT = "sort";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getSmId() { return smId; }

	public void setSmId(Long smId) { this.smId = smId; }

	public Long getPsmId() { return psmId; }

	public void setPsmId(Long psmId) { this.psmId = psmId; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public Boolean getNameEqual() { return nameEqual; }

	public void setNameEqual(Boolean nameEqual) { this.nameEqual = nameEqual; }

	public String getUrl() { return url; }

	public void setUrl(String url) { this.url = url; }

	public Boolean getUrlEqual() { return urlEqual; }

	public void setUrlEqual(Boolean urlEqual) { this.urlEqual = urlEqual; }

	public Integer getSort() { return sort; }

	public void setSort(Integer sort) { this.sort = sort; }

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

