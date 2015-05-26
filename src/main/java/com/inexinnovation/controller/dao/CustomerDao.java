package com.inexinnovation.controller.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.inexinnovation.controller.model.Customer;
import com.inexinnovation.controller.utility.RandomStringGenerator;

public class CustomerDao {

	private static ArrayList<Customer> CUSTOMER_IN_MEMORY_DAO = new ArrayList<Customer>();

	private static Logger LOGGER = LoggerFactory
			.getLogger(CustomerDao.class);

	private static String DATA_DIR = System.getenv("DATA_DIR");
	
	public ArrayList<Customer> getCustomers() {
		return CUSTOMER_IN_MEMORY_DAO;
	}  // ArrayList<Customer> getCustomers

	public Customer getCustomerById(String customerId)
	{
		int customerSize = CUSTOMER_IN_MEMORY_DAO.size();
		
		Customer returnCustomer = null;
		
		int targetIndex = -1;
		
		for (int index = 0; index < customerSize; index++)
		{
			LOGGER.debug(CUSTOMER_IN_MEMORY_DAO.get(index).getCustomerId() + " COMPARE AGAINST " + customerId);
			if(CUSTOMER_IN_MEMORY_DAO.get(index).getCustomerId().equalsIgnoreCase(customerId))
			{
				targetIndex = index;
				index = customerSize;
			}  // if
		}  // for

		if(targetIndex != -1)
		{
			returnCustomer = CUSTOMER_IN_MEMORY_DAO.get(targetIndex);
		}  // if
		
		return returnCustomer;
	}  // Customer getCustomerById
	
	public String getCustomerInCSV() throws IOException
	{
		CsvMapper mapper = new CsvMapper();
	    CsvSchema schema = mapper.schemaFor(Customer.class).withHeader();
	    return mapper.writer(schema).writeValueAsString(CUSTOMER_IN_MEMORY_DAO);
	}  // String getCustomerInCSV
	
	public synchronized void deleteCustomer(String customerId) throws JsonGenerationException, JsonMappingException, IOException
	{
		int customerSize = CUSTOMER_IN_MEMORY_DAO.size();
		
		int targetIndex = -1;
		
		for (int index = 0; index < customerSize; index++)
		{
			if(CUSTOMER_IN_MEMORY_DAO.get(index).getCustomerId().equalsIgnoreCase(customerId))
			{
				targetIndex = index;
				index = customerSize;
			}  // if
		}  // for

		if(targetIndex != -1)
		{
			CUSTOMER_IN_MEMORY_DAO.remove(targetIndex);
			
			ObjectMapper lMapper = new ObjectMapper();
			lMapper.writeValue(new File(DATA_DIR + "/data.json"),
					CUSTOMER_IN_MEMORY_DAO);
		}  // if
		
	}  // void deleteCustomer
	
	public synchronized Customer updateCustomer(String customerId, Customer customer) throws JsonGenerationException, JsonMappingException, IOException
	{
		int customerSize = CUSTOMER_IN_MEMORY_DAO.size();
		Customer returnCustomer = null;
		int targetIndex = -1;
		
		for (int index = 0; index < customerSize; index++)
		{
			if(CUSTOMER_IN_MEMORY_DAO.get(index).getCustomerId().equalsIgnoreCase(customerId))
			{
				targetIndex = index;
				index = customerSize;
			}  // if
		}  // for

		if(targetIndex != -1)
		{
			customer.setCustomerId(customerId);
			
			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerFirstName(customer.getCustomerFirstName());
			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerLastName(customer.getCustomerLastName());
			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerEmail(customer.getCustomerLastName());
			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerPhone(customer.getCustomerPhone());
			
			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerCountry(customer.getCustomerLastName());
			
			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerDataUsageAgreement(customer.getCustomerDataUsageAgreement());

			CUSTOMER_IN_MEMORY_DAO.get(targetIndex).setCustomerAdditionalComment(customer.getCustomerAdditionalComment());
		
			returnCustomer = CUSTOMER_IN_MEMORY_DAO.get(targetIndex);
		}  // if
		
		ObjectMapper lMapper = new ObjectMapper();
		lMapper.writeValue(new File(DATA_DIR + "/data.json"),
				CUSTOMER_IN_MEMORY_DAO);
		
		return returnCustomer;
	}  // Customer deleteCustomer
	
	public synchronized Customer saveCustomer(Customer customer)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		customer.setCustomerId(RandomStringGenerator.generator(30));

		CUSTOMER_IN_MEMORY_DAO.add(customer);
		
		ObjectMapper lMapper = new ObjectMapper();
		lMapper.writeValue(new File(DATA_DIR + "/data.json"),
				CUSTOMER_IN_MEMORY_DAO);
		
		return customer;
	}  // Customer saveCustomer

	public synchronized void readCustomers() throws JsonGenerationException,
			JsonMappingException, IOException {
		File lFile = new File(DATA_DIR + "/data.json");
		
		LOGGER.info("Loading existing configuration from: " + lFile.getAbsolutePath());
		
		// Only perform memory reading if file exists
		if (lFile.canRead()) {
			
			ArrayList<Customer> lTempCustomers = new ArrayList<Customer>();

			ObjectMapper lMapper = new ObjectMapper();
			lTempCustomers =lMapper.readValue(
					lFile, lMapper.getTypeFactory().constructCollectionType(ArrayList.class, Customer.class));

			CUSTOMER_IN_MEMORY_DAO.clear();

			for (Customer tempCustomer : lTempCustomers) {
				CUSTOMER_IN_MEMORY_DAO.add(tempCustomer);
			} // for
		} // if
	}  // void readCustomers()
}  // class CustomerDao
