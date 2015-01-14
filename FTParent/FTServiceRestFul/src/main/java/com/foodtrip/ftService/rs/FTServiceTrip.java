
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTTripController;
import com.foodtrip.ftmodelws.TripIDWS;
import com.foodtrip.ftmodelws.TripView;

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
    public TripView getTripGET(@PathParam(value = "endCompany")Long endCompany, @PathParam(value = "orderID")Long orderID) {
    	return new FTTripController().getTrip(endCompany,orderID);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    public TripView getTrip(TripIDWS tripID) {
    	return new FTTripController().getTrip(new Long(tripID.getEndCompany()),new Long(tripID.getId()));
    }
}
