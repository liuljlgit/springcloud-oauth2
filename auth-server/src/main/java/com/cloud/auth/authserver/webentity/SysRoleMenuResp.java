package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysRoleMenu;

@JSONType(includes = {
    SysRoleMenu.PROP_SRM_ID,
	SysRoleMenu.PROP_SR_ID,
	SysRoleMenu.PROP_SM_ID,
	SysRoleMenu.PROP_CREATE_TIME,
	SysRoleMenu.PROP_STATUS,
	SysRoleMenu.PROP_STATUS_TIME
})
public class SysRoleMenuResp extends SysRoleMenu{

	public SysRoleMenuResp(SysRoleMenu sysRoleMenu){
		CommonUtil.copyPropertiesIgnoreNull(sysRoleMenu,this);
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

