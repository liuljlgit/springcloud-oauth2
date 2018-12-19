package com.cloud.auth.authserver;

import com.gen.autocode.GenMain;
import com.gen.autocode.GenProp.GenProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServerApplicationTests {

	@Test
	public void autocode() {
		//数据库配置
		GenProperties.URL = "jdbc:mysql://192.168.1.139:3306/oauth2";
		GenProperties.NAME = "root";
		GenProperties.PASS = "root";
		GenProperties.DRIVER = "com.mysql.jdbc.Driver";
		//表配置:可配置表或者视图（视图仅用来查询,视图不生成缓存，没有主键，只生成查询方法）
		GenProperties.tablenames = "sys_user";
		GenProperties.useCache = Boolean.TRUE;
		//包路径配置
		GenProperties.entityPackageOutPath = "com.cloud.auth.authserver.entity";
		GenProperties.daoPackageOutPath = "com.cloud.auth.authserver.dao";
		GenProperties.servicePackageOutPath = "com.cloud.auth.authserver.service";
		GenProperties.redisPackageOutPath = "com.cloud.auth.authserver.cache";
		GenProperties.controllerPackageOutPath = "com.cloud.auth.authserver.controller";
		GenProperties.respPackageOutPath = "com.cloud.auth.authserver.webentity";
		GenProperties.xmlPackageOutPath = "mybatis.mapper.authserver.auto";
		GenProperties.customXmlPackageOutPath = "mybatis.mapper.authserver.custom";
		//有一下的模板可供选择。cache_template_v1、cache_template_v2、no_cache_template、sub_table_template、view_template
		GenProperties.templatePath = "template/cache_template_v2";
		//运行代码生成
		GenMain.main(null);
	}

}
