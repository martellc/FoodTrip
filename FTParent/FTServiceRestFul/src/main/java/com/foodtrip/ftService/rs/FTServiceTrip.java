
package com.foodtrip.ftService.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodtrip.ftcontroller.FTTripController;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.TripIDWS;
import com.foodtrip.ftmodelws.TripView;
import com.sun.jersey.api.client.ClientResponse.Status;
@Path("/trip")
public class FTServiceTrip {
    
    @GET
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @Path("/{orderID}/{endCompany}")
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public Response getTripGET(@PathParam(value = "endCompany")Long endCompany, @PathParam(value = "orderID")Long orderID) {
    	try {
			TripView view = new FTTripController().getTrip(orderID,endCompany);
			return Response.status(Status.ACCEPTED)
					.entity(view)
					.type(MediaType.APPLICATION_JSON)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTrip(TripIDWS tripID) {
    	if (tripID == null || tripID.getId() == null) {
    		return null;
    	}
    	
    	String[] split = tripID.getId().split("-");
    	if(split.length < 2) {
    		return null;
    	}
    	
    	Long id = Long.valueOf(split[0]);
    	Long endCompany =  Long.valueOf(split[1]);
    	
    	try {
			TripView view = new FTTripController().getTrip(id,endCompany);
			return Response.status(Status.ACCEPTED)
					.entity(view)
					.type(MediaType.APPLICATION_JSON)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
}
