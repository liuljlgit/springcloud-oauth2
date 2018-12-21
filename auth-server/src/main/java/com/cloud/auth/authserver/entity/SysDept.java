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
 * SysDept 实体类
 * @author lijun
 */
public class SysDept extends PageEntity implements Serializable {
	/**
	* field comment: 主键 
	*/
	private Long sdId;

	/**
	* field comment: 父级部门 
	*/
	private Long psdId;

	/**
	* field comment: 归属公司 
	*/
	private Long scId;

	/**
	* field comment: 部门名称 
	*/
	private String deptName;

	/**
	* field comment: 部门名称 
	*/
	private transient Boolean deptNameEqual = Boolean.FALSE;

	/**
	* field comment: 部门地址 
	*/
	private String address;

	/**
	* field comment: 部门地址 
	*/
	private transient Boolean addressEqual = Boolean.FALSE;

	/**
	* field comment: 部门电话号码 
	*/
	private String phone;

	/**
	* field comment: 部门电话号码 
	*/
	private transient Boolean phoneEqual = Boolean.FALSE;

	/**
	* field comment: email地址 
	*/
	private String email;

	/**
	* field comment: email地址 
	*/
	private transient Boolean emailEqual = Boolean.FALSE;

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

	public static final transient String PROP_SD_ID = "sdId";
	public static final transient String TABLE_SD_ID = "sd_id";
	public static final transient String PROP_PSD_ID = "psdId";
	public static final transient String TABLE_PSD_ID = "psd_id";
	public static final transient String PROP_SC_ID = "scId";
	public static final transient String TABLE_SC_ID = "sc_id";
	public static final transient String PROP_DEPT_NAME = "deptName";
	public static final transient String TABLE_DEPT_NAME = "dept_name";
	public static final transient String PROP_ADDRESS = "address";
	public static final transient String TABLE_ADDRESS = "address";
	public static final transient String PROP_PHONE = "phone";
	public static final transient String TABLE_PHONE = "phone";
	public static final transient String PROP_EMAIL = "email";
	public static final transient String TABLE_EMAIL = "email";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getSdId() { return sdId; }

	public void setSdId(Long sdId) { this.sdId = sdId; }

	public Long getPsdId() { return psdId; }

	public void setPsdId(Long psdId) { this.psdId = psdId; }

	public Long getScId() { return scId; }

	public void setScId(Long scId) { this.scId = scId; }

	public String getDeptName() { return deptName; }

	public void setDeptName(String deptName) { this.deptName = deptName; }

	public Boolean getDeptNameEqual() { return deptNameEqual; }

	public void setDeptNameEqual(Boolean deptNameEqual) { this.deptNameEqual = deptNameEqual; }

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }

	public Boolean getAddressEqual() { return addressEqual; }

	public void setAddressEqual(Boolean addressEqual) { this.addressEqual = addressEqual; }

	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	public Boolean getPhoneEqual() { return phoneEqual; }

	public void setPhoneEqual(Boolean phoneEqual) { this.phoneEqual = phoneEqual; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public Boolean getEmailEqual() { return emailEqual; }

	public void setEmailEqual(Boolean emailEqual) { this.emailEqual = emailEqual; }

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

