package com.inexinnovation.controller.service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inexinnovation.controller.dao.CustomerDao;
import com.inexinnovation.controller.model.Customer;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

@Produces("application/json; charset=UTF-8")
@Path("/customer")
public class CustomerService {
	private static Logger LOGGER = LoggerFactory
			.getLogger(CustomerService.class);

	@Resource(shareable = true, name = "getCustomerDao")
	private CustomerDao customerDao;

	@GET
	@Path("/_all")
	@ApiOperation(value = "Find session by session ID", notes = "Return the session record that this session belong")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Session ID not found"),
			@ApiResponse(code = 403, message = "Not authorized") })
	public Response getAllCustomers() {
		try {

			return Response.ok().entity(customerDao.getCustomers()).build();
		} // try
		catch (Exception pExeception) {
			LOGGER.error(pExeception.toString());
			return Response.serverError().build();
		} // catch
	} // Session getSessionById

	@GET
	@Path("/_all/csv")
	@Produces("text/csv; charset=UTF-8")
	@ApiOperation(value = "Find session by session ID", notes = "Return the session record that this session belong")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Session ID not found"),
			@ApiResponse(code = 403, message = "Not authorized") })
	public Response getAllCustomersInCSV() {
		try {

			return Response
					.ok()
					.entity((Object) customerDao.getCustomerInCSV())
					.header("Content-Disposition",
							"attachment; filename=customer.csv").build();
		} // try
		catch (Exception pExeception) {
			LOGGER.error(pExeception.toString());
			return Response.serverError().build();
		} // catch
	} // Session getSessionById

	@GET
	@Path("/{customerId}")
	@ApiOperation(value = "Find session by session ID", notes = "Return the session record that this session belong")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Session ID not found"),
			@ApiResponse(code = 403, message = "Not authorized") })
	public Response geCustomerById(@PathParam("customerId") String pCustomerId) {
		try {
			Customer returnCustomer = customerDao.getCustomerById(pCustomerId);

			if (returnCustomer != null) {
				return Response.ok()
						.entity(customerDao.getCustomerById(pCustomerId))
						.build();
			} // if
			else {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("{\"status\":\"Not Found\"}").build();
			} // else
		} // try
		catch (Exception pExeception) {
			LOGGER.error(pExeception.toString());
			return Response.serverError().build();

		} // catch
	} // Session getSessionById

	@DELETE
	@Path("/{customerId}")
	@ApiOperation(value = "Delete session by session ID")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Session ID not found"),
			@ApiResponse(code = 403, message = "Not authorized") })
	public Response deleteCustomerById(
			@PathParam("customerId") String pCustomerId) {
		try {
			Customer returnCustomer = customerDao.getCustomerById(pCustomerId);

			if (returnCustomer != null) {
				customerDao.deleteCustomer(pCustomerId);
				return Response.ok().entity("{\"status\":\"Success\"}").build();
			} // if
			else {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("{\"status\":\"Not Found\"}").build();
			} // else
		} // try
		catch (Exception pExeception) {
			LOGGER.error(pExeception.toString());
			return Response.serverError().build();

		} // catch
	} // Session getSessionById

	@POST
	@Consumes("application/json")
	@Path("/")
	@ApiOperation(value = "Create a customer record", notes = "Create a customer record")
	@ApiResponses(value = { @ApiResponse(code = 403, message = "Not authorized") })
	public Response createCustomer(Customer customer) {
		try {
			return Response.ok().entity(customerDao.saveCustomer(customer))
					.build();
		} // try
		catch (Exception pExeception) {
			LOGGER.error(pExeception.toString());
			return Response.serverError().build();
		} // catch
	} // Session createCustomer

	@PUT
	@Path("/{customerId}")
	@ApiOperation(value = "Update a customer record", notes = "Update a customer record")
	@ApiResponses(value = { @ApiResponse(code = 403, message = "Not authorized") })
	public Response updateCustomer(@PathParam("customerId") String customerId,
			Customer customer) {
		try {
			Customer returnCustomer = customerDao.getCustomerById(customerId);

			if (returnCustomer != null) {
				return Response
						.ok()
						.entity(customerDao.updateCustomer(customerId,
								returnCustomer)).build();
			} // if
			else {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("{\"status\":\"Not Found\"}").build();
			} // else
		} // try
		catch (Exception pExeception) {
			LOGGER.error(pExeception.toString());
			return Response.serverError().build();
		} // catch
	} // Session getSessionById
} // class SessionService
