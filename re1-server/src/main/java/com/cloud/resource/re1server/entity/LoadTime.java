package com.cloud.resource.re1server.entity;

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


/**
 * LoadTime 实体类
 * @author lijun
 */
public class LoadTime extends PageEntity implements Serializable {
	/**
	* field comment: 实际负荷导入数据记录ID 
	*/
	private Long ltId;

	/**
	* field comment: 电企业ID：来源于cloud_sys.entity.entity_id 
	*/
	private Integer entityId;

	/**
	* field comment: 导入开始时间 
	*/
	private Date startTime;

	/**
	* field comment: 导入开始时间 
	*/
	private transient Date startTimeStart;

	/**
	* field comment: 导入开始时间 
	*/
	private transient Date startTimeEnd;

	/**
	* field comment: 导入结束时间 
	*/
	private Date endTime;

	/**
	* field comment: 导入结束时间 
	*/
	private transient Date endTimeStart;

	/**
	* field comment: 导入结束时间 
	*/
	private transient Date endTimeEnd;

	/**
	* field comment: 状态 (0) 未处理 （1）已处理 
	*/
	private Byte status;

	/**
	* field comment: 创建时间 
	*/
	private Date createTime;

	/**
	* field comment: 创建时间 
	*/
	private transient Date createTimeStart;

	/**
	* field comment: 创建时间 
	*/
	private transient Date createTimeEnd;

	/**
	* field comment: 更新时间 
	*/
	private Date statusTime;

	/**
	* field comment: 更新时间 
	*/
	private transient Date statusTimeStart;

	/**
	* field comment: 更新时间 
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

	public static final transient String PROP_LT_ID = "ltId";
	public static final transient String TABLE_LT_ID = "lt_id";
	public static final transient String PROP_ENTITY_ID = "entityId";
	public static final transient String TABLE_ENTITY_ID = "entity_id";
	public static final transient String PROP_START_TIME = "startTime";
	public static final transient String TABLE_START_TIME = "start_time";
	public static final transient String PROP_END_TIME = "endTime";
	public static final transient String TABLE_END_TIME = "end_time";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getLtId() { return ltId; }

	public void setLtId(Long ltId) { this.ltId = ltId; }

	public Integer getEntityId() { return entityId; }

	public void setEntityId(Integer entityId) { this.entityId = entityId; }

	public Date getStartTime() { return startTime; }

	public void setStartTime(Date startTime) { this.startTime = startTime; }

	public Date getStartTimeStart() { return startTimeStart; }

	public void setStartTimeStart(Date startTimeStart) { this.startTimeStart = startTimeStart; }

	public Date getStartTimeEnd() { return startTimeEnd; }

	public void setStartTimeEnd(Date startTimeEnd) { this.startTimeEnd = startTimeEnd; }

	public Date getEndTime() { return endTime; }

	public void setEndTime(Date endTime) { this.endTime = endTime; }

	public Date getEndTimeStart() { return endTimeStart; }

	public void setEndTimeStart(Date endTimeStart) { this.endTimeStart = endTimeStart; }

	public Date getEndTimeEnd() { return endTimeEnd; }

	public void setEndTimeEnd(Date endTimeEnd) { this.endTimeEnd = endTimeEnd; }

	public Byte getStatus() { return status; }

	public void setStatus(Byte status) { this.status = status; }

	public Date getCreateTime() { return createTime; }

	public void setCreateTime(Date createTime) { this.createTime = createTime; }

	public Date getCreateTimeStart() { return createTimeStart; }

	public void setCreateTimeStart(Date createTimeStart) { this.createTimeStart = createTimeStart; }

	public Date getCreateTimeEnd() { return createTimeEnd; }

	public void setCreateTimeEnd(Date createTimeEnd) { this.createTimeEnd = createTimeEnd; }

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

