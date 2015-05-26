package com.inexinnovation.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inexinnovation.controller.dao.CustomerDao;

@Configuration
public class DAOConfig {
	
	private static Logger LOGGER = LoggerFactory
			.getLogger(DAOConfig.class);
	
	@Bean
	public CustomerDao getCustomerDao()
	{
		CustomerDao customerDao = new CustomerDao();
		try {
			customerDao.readCustomers();
		} catch (Exception e) {
			LOGGER.error("Error reading from existing customer data, will create a new configuration", e);
		}  // catch
		
		return customerDao;
	}  // CustomerDao getCustomerDao
}
