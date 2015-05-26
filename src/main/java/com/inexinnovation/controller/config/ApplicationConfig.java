package com.inexinnovation.controller.config;

import java.util.Arrays;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.inexinnovation.controller.service.CustomerService;

@Configuration
public class ApplicationConfig {
	@Bean(destroyMethod = "shutdown")
	

	public SpringBus cxf()
	{
		return new SpringBus();
	}  // public SpringBus cxf
	
    @Bean
    public JacksonJsonProvider getJSONProvider() {
        return new JacksonJsonProvider();
    }  // public JacksonJsonProvider getJSONProvider()
    
	@Bean
	public CustomerService getCustomerService()
	{
		return new CustomerService();
	}  // public CustomerService getCustomerService()
	
	@Bean
	public Server initJAXRSServer() {
		JAXRSServerFactoryBean lFactory = RuntimeDelegate.getInstance().createEndpoint(new Application(),
				JAXRSServerFactoryBean.class);
		lFactory.setServiceBeans(Arrays.<Object>asList(getCustomerService()));
		
		lFactory.setAddress(lFactory.getAddress());
	
		lFactory.setProviders(Arrays.<Object>asList(getJSONProvider()));
		
		return lFactory.create();
	}  // Server initJAXRSServer
}
