package com.example.mutilDatasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


  @Configuration //之策到spring容器中
  @MapperScan(basePackages = "com.example.sourceone.mapper", sqlSessionFactoryRef =  "test1SqlSessionFactory")
  public class DataSourceOne {
          @Bean(name = "test1DataSource")
          @ConfigurationProperties(prefix = "spring.datasource.sourceone")
          @Primary
          public DataSource testDataSource() {
                       return DataSourceBuilder.create().build();
           }


        @Bean(name = "test1SqlSessionFactory")
        @Primary
        public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
                        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
                        bean.setDataSource(dataSource);
                        //读取mybatis小配置文件
                       // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
                        return bean.getObject();
         }

         @Bean(name = "test1TransactionManager")
         @Primary
         public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
                        return new DataSourceTransactionManager(dataSource);
                     }


        @Bean(name = "test1SqlSessionTemplate")
        @Primary
        public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
                         return new SqlSessionTemplate(sqlSessionFactory);
        }
  }

