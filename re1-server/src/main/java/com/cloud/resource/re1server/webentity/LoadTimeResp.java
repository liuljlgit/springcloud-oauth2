package com.cloud.resource.re1server.webentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.DateUtil;
import java.util.Date;
import com.cloud.resource.re1server.entity.LoadTime;

@JSONType(includes = {
    LoadTime.PROP_LT_ID,
	LoadTime.PROP_ENTITY_ID,
	LoadTime.PROP_START_TIME,
	LoadTime.PROP_END_TIME,
	LoadTime.PROP_STATUS,
	LoadTime.PROP_CREATE_TIME,
	LoadTime.PROP_STATUS_TIME
})
public class LoadTimeResp extends LoadTime{

	public LoadTimeResp(LoadTime loadTime){
		CommonUtil.copyPropertiesIgnoreNull(loadTime,this);
	}

    @Override
    @JSONField(format = DateUtil.DEFAUL_FORMAT)
    public Date getStartTime() {
        return super.getStartTime();
    }

    @Override
    @JSONField(format = DateUtil.DEFAUL_FORMAT)
    public Date getEndTime() {
        return super.getEndTime();
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

