package com.cloud.resource.re1server;

import com.gen.autocode.common.GenProperties;
import com.gen.autocode.engine.GenMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Re1ServerApplicationTests {

	@Test
	public void autocode() {
		//数据库配置
		GenProperties.URL = "jdbc:mysql://192.168.1.136:3306/oauth2";
		GenProperties.NAME = "root";
		GenProperties.PASS = "root";
		GenProperties.DRIVER = "com.mysql.jdbc.Driver";
		//表配置:可配置表或者视图（视图仅用来查询,视图不生成缓存，没有主键，只生成查询方法）
		GenProperties.tablenames = "load_time";
		GenProperties.useCache = Boolean.TRUE;
		//包路径配置
		GenProperties.entityPackageOutPath = "com.cloud.resource.re1server.entity";
		GenProperties.daoPackageOutPath = "com.cloud.resource.re1server.dao";
		GenProperties.servicePackageOutPath = "com.cloud.resource.re1server.service";
		GenProperties.redisPackageOutPath = "com.cloud.resource.re1server.cache";
		GenProperties.controllerPackageOutPath = "com.cloud.resource.re1server.controller";
		GenProperties.respPackageOutPath = "com.cloud.resource.re1server.webentity";
		GenProperties.xmlPackageOutPath = "mybatis.mapper.re1server.auto";
		//有一下的模板可供选择。cache_template、no_cache_template、sub_table_template、view_template
		GenProperties.templatePath = "template/cache_template";
		//运行代码生成
		GenMain.main(null);
	}

}

