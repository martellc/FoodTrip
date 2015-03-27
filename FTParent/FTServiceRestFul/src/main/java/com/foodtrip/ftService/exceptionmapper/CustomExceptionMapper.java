package com.foodtrip.ftService.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.foodtrip.ftcontroller.exception.FoodtripException;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Object GENERIC_ERROR_CODE = "00";

	public Response toResponse(Throwable bex) {
		if(bex instanceof FoodtripException) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(((FoodtripException)bex).getBusinessCode())
					.build();	
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(GENERIC_ERROR_CODE)
				.build();
		
	}
}