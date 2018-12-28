package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysPermission;

@JSONType(includes = {
    SysPermission.PROP_SP_ID,
	SysPermission.PROP_PERM_NAME,
	SysPermission.PROP_DESCRIPTION,
	SysPermission.PROP_CREATE_TIME,
	SysPermission.PROP_STATUS,
	SysPermission.PROP_STATUS_TIME
})
public class SysPermissionResp extends SysPermission{

	public SysPermissionResp(SysPermission sysPermission){
		CommonUtil.copyPropertiesIgnoreNull(sysPermission,this);
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

