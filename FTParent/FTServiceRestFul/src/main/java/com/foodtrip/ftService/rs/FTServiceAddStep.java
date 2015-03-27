
package com.foodtrip.ftService.rs;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodtrip.ftcontroller.FTTripController;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.NewStepWS;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/addStep")
public class FTServiceAddStep {
    
      
    @POST
    public Response addStep(NewStepWS step) throws FoodtripException {
    	try {
			new FTTripController().addStep(step.getEndPoint(), step.getOrderId(), step.getCompany(),step.getPoint());
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
