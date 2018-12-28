package com.cloud.auth.authserver.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.auth.authserver.entity.SysDept;

@JSONType(includes = {
    SysDept.PROP_SD_ID,
	SysDept.PROP_PSD_ID,
	SysDept.PROP_SC_ID,
	SysDept.PROP_DEPT_NAME,
	SysDept.PROP_ADDRESS,
	SysDept.PROP_PHONE,
	SysDept.PROP_EMAIL,
	SysDept.PROP_CREATE_TIME,
	SysDept.PROP_STATUS,
	SysDept.PROP_STATUS_TIME
})
public class SysDeptResp extends SysDept{

	public SysDeptResp(SysDept sysDept){
		CommonUtil.copyPropertiesIgnoreNull(sysDept,this);
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

