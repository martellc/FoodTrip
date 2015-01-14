
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.foodtrip.ftcontroller.FTTripController;
import com.foodtrip.ftmodelws.NewStepWS;

@Path("/addStep")
public class FTServiceAddStep {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @POST
    public void addStep(NewStepWS step) {
    	new FTTripController().addStep(step.getEndPoint(), step.getOrderId(), step.getCompany());
    }
}
