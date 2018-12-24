package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysUser;

@JSONType(includes = {
    SysUser.PROP_SU_ID,
	SysUser.PROP_SD_ID,
	SysUser.PROP_ACCOUNT,
	SysUser.PROP_PASSWD,
	SysUser.PROP_PHONE,
	SysUser.PROP_EMAIL,
	SysUser.PROP_SEX,
	SysUser.PROP_BIRTHDAY,
	SysUser.PROP_CREATE_TIME,
	SysUser.PROP_STATUS,
	SysUser.PROP_STATUS_TIME
})
public class SysUserResp extends SysUser{

	public SysUserResp(SysUser sysUser){
		CommonUtil.copyPropertiesIgnoreNull(sysUser,this);
	}

    @Override
    @JSONField(format = DateUtil.DEFAUL_FORMAT)
    public Date getBirthday() {
        return super.getBirthday();
    }

    @Override
    @JSONField(format = DateUtil.DEFAUL_FORMAT)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Override
    @JSONField(format = DateUtil.DEFAUL_FORMAT)
    public Date getStatusTime() {
        return super.getStatusTime();
    }



}

