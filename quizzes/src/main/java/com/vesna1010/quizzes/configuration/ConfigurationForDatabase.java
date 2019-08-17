package com.vesna1010.quizzes.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db/database.properties")
@ComponentScan(basePackages = { "com.vesna1010.quizzes.dao.impl", "com.vesna1010.quizzes.service.impl" })
public class ConfigurationForDatabase {

	@Autowired
	private Environment environment;

	@Bean
	public HibernateTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory().getObject());
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setPackagesToScan("com.vesna1010.quizzes.model");

		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setUrl(environment.getProperty("jdbc.url"));
		dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		dataSource.setUsername(environment.getProperty("jdbc.username"));

		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();

		properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.enable_lazy_load_no_trans",
				environment.getProperty("hibernate.enable_lazy_load_no_trans"));
		properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

		return properties;
	}

}
