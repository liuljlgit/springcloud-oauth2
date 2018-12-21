package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysDeptRole;

@JSONType(includes = {
    SysDeptRole.PROP_SDR_ID,
	SysDeptRole.PROP_SD_ID,
	SysDeptRole.PROP_SR_ID,
	SysDeptRole.PROP_CREATE_TIME,
	SysDeptRole.PROP_STATUS,
	SysDeptRole.PROP_STATUS_TIME
})
public class SysDeptRoleResp extends SysDeptRole{

	public SysDeptRoleResp(SysDeptRole sysDeptRole){
		CommonUtil.copyPropertiesIgnoreNull(sysDeptRole,this);
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

