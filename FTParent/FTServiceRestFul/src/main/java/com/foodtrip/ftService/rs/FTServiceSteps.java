
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.foodtrip.ftcontroller.FTOperationController;

@Path("/Steps")
public class FTServiceSteps {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @POST
    public void getSteps(Long orderID) {
    	new FTOperationController().getSteps(orderID);
    }
}
