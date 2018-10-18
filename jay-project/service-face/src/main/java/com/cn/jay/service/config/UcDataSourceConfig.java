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
@MapperScan(basePackages = {"com.cn.jay.business.data1.mapper"}, sqlSessionFactoryRef = "ucSqlSessionFactory")
public class UcDataSourceConfig {
	@Primary
    @Bean(name = "ucDataSource")
    @ConfigurationProperties(prefix = "datasource.user-server")
    public DataSource dataSource() {
		DataSource s = DataSourceBuilder.create().build();
        return s;
    }

    @Primary
    @Bean(name = "ucTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("ucDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "ucSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ucDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "ucSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("ucSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
