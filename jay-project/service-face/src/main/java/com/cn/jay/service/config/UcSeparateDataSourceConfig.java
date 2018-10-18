package com.cn.jay.service.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {"com.cn.jay.business.data2.mapper"}, sqlSessionFactoryRef = "ucSeparateSqlSessionFactory")
public class UcSeparateDataSourceConfig {
	@Primary
    @Bean(name = "ucSeparateDataSource")
    @ConfigurationProperties(prefix = "datasource.user-server-separate")
    public DataSource dataSource() {
		DataSource s = DataSourceBuilder.create().build();
        return s;
    }

    @Primary
    @Bean(name = "ucSeparateTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("ucSeparateDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "ucSeparateSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ucSeparateDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:separate/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "ucSeparateSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("ucSeparateSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
