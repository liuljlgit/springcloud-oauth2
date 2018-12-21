package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysRolePermission;

@JSONType(includes = {
    SysRolePermission.PROP_SRP_ID,
	SysRolePermission.PROP_SR_ID,
	SysRolePermission.PROP_SP_ID,
	SysRolePermission.PROP_CREATE_TIME,
	SysRolePermission.PROP_STATUS,
	SysRolePermission.PROP_STATUS_TIME
})
public class SysRolePermissionResp extends SysRolePermission{

	public SysRolePermissionResp(SysRolePermission sysRolePermission){
		CommonUtil.copyPropertiesIgnoreNull(sysRolePermission,this);
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

