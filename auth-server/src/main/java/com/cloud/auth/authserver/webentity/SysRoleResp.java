package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysRole;

@JSONType(includes = {
    SysRole.PROP_SR_ID,
	SysRole.PROP_ROLE_NAME,
	SysRole.PROP_ROLE_CATEGORY,
	SysRole.PROP_ROLE_TYPE,
	SysRole.PROP_DESCRIPTION,
	SysRole.PROP_CREATE_TIME,
	SysRole.PROP_STATUS,
	SysRole.PROP_STATUS_TIME
})
public class SysRoleResp extends SysRole{

	public SysRoleResp(SysRole sysRole){
		CommonUtil.copyPropertiesIgnoreNull(sysRole,this);
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

