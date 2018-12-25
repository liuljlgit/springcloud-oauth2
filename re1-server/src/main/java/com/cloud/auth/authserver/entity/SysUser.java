package com.cloud.auth.authserver.entity;

import com.cloud.common.simplequery.IntervalEntity;
import com.cloud.common.webcomm.PageEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * SysUser 实体类
 * @author lijun
 */
public class SysUser extends PageEntity implements Serializable {
	/**
	* field comment: 主键 
	*/
	private Long suId;

	/**
	* field comment: 所属部门 
	*/
	private Long sdId;

	/**
	* field comment: 账号 
	*/
	private String account;

	/**
	* field comment: 账号 
	*/
	private transient Boolean accountEqual = Boolean.FALSE;

	/**
	* field comment: 密码 
	*/
	private String passwd;

	/**
	* field comment: 密码 
	*/
	private transient Boolean passwdEqual = Boolean.FALSE;

	/**
	* field comment: 电话号码 
	*/
	private String phone;

	/**
	* field comment: 电话号码 
	*/
	private transient Boolean phoneEqual = Boolean.FALSE;

	/**
	* field comment: 邮件地址 
	*/
	private String email;

	/**
	* field comment: 邮件地址 
	*/
	private transient Boolean emailEqual = Boolean.FALSE;

	/**
	* field comment: 【性别】1、男性，2、女性 
	*/
	private Byte sex;

	/**
	* field comment: 出生日期 
	*/
	private Date birthday;

	/**
	* field comment: 出生日期 
	*/
	private transient Date birthdayStart;

	/**
	* field comment: 出生日期 
	*/
	private transient Date birthdayEnd;

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

	public static final transient String PROP_SU_ID = "suId";
	public static final transient String TABLE_SU_ID = "su_id";
	public static final transient String PROP_SD_ID = "sdId";
	public static final transient String TABLE_SD_ID = "sd_id";
	public static final transient String PROP_ACCOUNT = "account";
	public static final transient String TABLE_ACCOUNT = "account";
	public static final transient String PROP_PASSWD = "passwd";
	public static final transient String TABLE_PASSWD = "passwd";
	public static final transient String PROP_PHONE = "phone";
	public static final transient String TABLE_PHONE = "phone";
	public static final transient String PROP_EMAIL = "email";
	public static final transient String TABLE_EMAIL = "email";
	public static final transient String PROP_SEX = "sex";
	public static final transient String TABLE_SEX = "sex";
	public static final transient String PROP_BIRTHDAY = "birthday";
	public static final transient String TABLE_BIRTHDAY = "birthday";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getSuId() { return suId; }

	public void setSuId(Long suId) { this.suId = suId; }

	public Long getSdId() { return sdId; }

	public void setSdId(Long sdId) { this.sdId = sdId; }

	public String getAccount() { return account; }

	public void setAccount(String account) { this.account = account; }

	public Boolean getAccountEqual() { return accountEqual; }

	public void setAccountEqual(Boolean accountEqual) { this.accountEqual = accountEqual; }

	public String getPasswd() { return passwd; }

	public void setPasswd(String passwd) { this.passwd = passwd; }

	public Boolean getPasswdEqual() { return passwdEqual; }

	public void setPasswdEqual(Boolean passwdEqual) { this.passwdEqual = passwdEqual; }

	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	public Boolean getPhoneEqual() { return phoneEqual; }

	public void setPhoneEqual(Boolean phoneEqual) { this.phoneEqual = phoneEqual; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public Boolean getEmailEqual() { return emailEqual; }

	public void setEmailEqual(Boolean emailEqual) { this.emailEqual = emailEqual; }

	public Byte getSex() { return sex; }

	public void setSex(Byte sex) { this.sex = sex; }

	public Date getBirthday() { return birthday; }

	public void setBirthday(Date birthday) { this.birthday = birthday; }

	public Date getBirthdayStart() { return birthdayStart; }

	public void setBirthdayStart(Date birthdayStart) { this.birthdayStart = birthdayStart; }

	public Date getBirthdayEnd() { return birthdayEnd; }

	public void setBirthdayEnd(Date birthdayEnd) { this.birthdayEnd = birthdayEnd; }

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

