//package kr.co.queenssmile.core.config.database;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.sql.Driver;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Slf4j
//@Configuration
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceHealthContributorAutoConfiguration.class})
//public class DatabaseConfig {
//
//  @Value("${spring.datasource.url}")
//  public String url;
//
//  @Value("${spring.datasource.username}")
//  public String username;
//
//  @Value("${spring.datasource.password}")
//  public String password;
//
//  @Value("${spring.datasource.slave1}")
//  public String slave1;
//
//  public DataSource createDataSource(String url) {
//
//
//    try {
//      log.info("createDataSource:url ::: {}", url);
//
//      SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//      dataSource.setUrl(url);
//      dataSource.setDriverClass((Class<? extends Driver>) Class.forName("com.mysql.cj.jdbc.Driver"));
//      dataSource.setUsername(username);
//      dataSource.setPassword(password);
//
//      return dataSource;
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//      log.error("createDataSource", e);
//    }
//    throw new RuntimeException("DB Connection Error");
//  }
//
//  @Bean
//  public DataSource routingDataSource() {
//    ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
//
//    DataSource master = createDataSource(url);
//
//    Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
//    dataSourceMap.put("master", master);
//    dataSourceMap.put("slave_1", createDataSource(slave1));
//
//    replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
//    replicationRoutingDataSource.setDefaultTargetDataSource(master);
//    return replicationRoutingDataSource;
//  }
//
//  @Bean
//  public DataSource dataSource() {
//    return new LazyConnectionDataSourceProxy(routingDataSource());
//  }
//
//  @Bean
//  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//    entityManagerFactoryBean.setDataSource(dataSource());
//    entityManagerFactoryBean.setPackagesToScan("kr.co.queenssmile.core.domain");
//    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//
//    return entityManagerFactoryBean;
//  }
//
//  @Bean
//  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//    JpaTransactionManager tm = new JpaTransactionManager();
//    tm.setEntityManagerFactory(entityManagerFactory);
//    return tm;
//  }
//
//  @Getter
//  @Setter
//  static class Slave {
//    private String name;
//    private String url;
//  }
//}
//
