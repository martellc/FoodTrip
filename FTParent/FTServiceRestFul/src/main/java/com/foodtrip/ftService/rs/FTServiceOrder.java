
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodtrip.ftcontroller.FTOrderController;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.OrderWS;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/order")
public class FTServiceOrder {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public Response getOrder(@PathParam(value = "id") Long id) {
        
        try {
        	OrderWS order = new FTOrderController().getOrder(id);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(order)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    public Response updateOrder(OrderWS order) {
    	try {
    		OrderWS updatedOrder = new FTOrderController().updateOrder(order);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(updatedOrder)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
}
