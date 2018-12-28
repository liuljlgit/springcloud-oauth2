package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysMenu;

@JSONType(includes = {
    SysMenu.PROP_SM_ID,
	SysMenu.PROP_PSM_ID,
	SysMenu.PROP_NAME,
	SysMenu.PROP_URL,
	SysMenu.PROP_SORT,
	SysMenu.PROP_CREATE_TIME,
	SysMenu.PROP_STATUS,
	SysMenu.PROP_STATUS_TIME
})
public class SysMenuResp extends SysMenu{

	public SysMenuResp(SysMenu sysMenu){
		CommonUtil.copyPropertiesIgnoreNull(sysMenu,this);
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

