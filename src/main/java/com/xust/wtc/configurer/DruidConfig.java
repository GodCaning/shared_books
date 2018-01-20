package com.xust.wtc.configurer;

import com.alibaba.druid.pool.DruidDataSource;
//import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DruidConfig implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	@Bean // 声明其为Bean实例
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
//		datasource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sharedBooks?useUnicode=true&characterEncoding=utf-8");
		datasource.setUrl(propertyResolver.getProperty("url"));
//		datasource.setDriverClass("com.mysql.jdbc.Driver");
		datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
//		datasource.setDriverClass("com.mysql.jdbc.Driver");
		datasource.setUsername(propertyResolver.getProperty("username"));
//		datasource.setUsername(propertyResolver.getProperty("username"));
//		datasource.setPassword(propertyResolver.getProperty("password"));
		datasource.setPassword("wangcan123");
//		datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initialSize")));
//		datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("minIdle")));
//		datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("maxWait")));
//		datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("maxActive")));
//		datasource.setMinEvictableIdleTimeMillis(
//				Long.valueOf(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
		return datasource;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
	}
}