package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysUserRole;

@JSONType(includes = {
    SysUserRole.PROP_SUR_ID,
	SysUserRole.PROP_SU_ID,
	SysUserRole.PROP_SR_ID,
	SysUserRole.PROP_CREATE_TIME,
	SysUserRole.PROP_STATUS,
	SysUserRole.PROP_STATUS_TIME
})
public class SysUserRoleResp extends SysUserRole{

	public SysUserRoleResp(SysUserRole sysUserRole){
		CommonUtil.copyPropertiesIgnoreNull(sysUserRole,this);
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

