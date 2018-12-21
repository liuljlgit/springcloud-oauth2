package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysCompany;

@JSONType(includes = {
    SysCompany.PROP_SC_ID,
	SysCompany.PROP_COMPANY_NAME,
	SysCompany.PROP_ADDRESS,
	SysCompany.PROP_PHONE,
	SysCompany.PROP_EMAIL,
	SysCompany.PROP_CREATE_TIME,
	SysCompany.PROP_STATUS,
	SysCompany.PROP_STATUS_TIME
})
public class SysCompanyResp extends SysCompany{

	public SysCompanyResp(SysCompany sysCompany){
		CommonUtil.copyPropertiesIgnoreNull(sysCompany,this);
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

