
package com.foodtrip.ftService.rs;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodtrip.ftcontroller.FTTripController;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.ConfirmStepWS;
import com.foodtrip.ftmodelws.StepWS;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/step")
public class FTServiceSteps {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    
    @Path("/{orderID}")
    @GET
    @Produces({MediaType.APPLICATION_JSON })		
    public Response getSteps(@PathParam(value = "orderID") Long orderID) {

    	List<StepWS> steps;
		try {
			steps = new FTTripController().getCurrentSteps(orderID);
			return Response.status(Status.ACCEPTED)
					.entity(steps)
					.type(MediaType.APPLICATION_JSON)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
    
    @Path("/confirm")
    @POST
    public Response confirmOrDenyStep(ConfirmStepWS confirmStep) throws FoodtripException {
    	try {
			new FTTripController().confirmOrDenyStep(confirmStep.getNotificationID(), confirmStep.getCompany(),confirmStep.isConfirmed());
			return Response.status(Status.ACCEPTED)
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
