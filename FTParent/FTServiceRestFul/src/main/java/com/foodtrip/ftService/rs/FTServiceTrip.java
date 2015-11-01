
package com.foodtrip.ftService.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.foodtrip.ftcontroller.FTTripController;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.TripIDWS;
import com.foodtrip.ftmodelws.TripView;
import com.sun.jersey.api.client.ClientResponse.Status;
@Path("/trip")
public class FTServiceTrip {
	private static final Logger logger = Logger.getLogger(FTServiceTrip.class);


	@GET
	@Produces("text/plain")
	public String getIt() {
		return "Hi there!";
	}

	@Path("/{orderID}/{endStepID}")
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response getTrip(@PathParam(value = "endStepID")Long endStepID, @PathParam(value = "orderID")Long orderID) {
		try {
			TripView view = new FTTripController().getTrip(orderID,endStepID);
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

		Long orderID = null;
		Long endStepID =null;

		if(tripID != null && tripID.getId() != null) {
			try {
				String[] split = safeSplit(tripID.getId());
				orderID = Long.valueOf(split[0]);
				if (split.length ==2) endStepID =  Long.valueOf(split[1]);
			}catch(Exception e) {
				logger.error("error",e);
				orderID=null;
				endStepID = null;
			}
		}

		try {
			TripView view = new FTTripController().getTrip(orderID,endStepID);
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

	private String[] safeSplit(String id) {
		String[] retval;
		if (!id.contains("-")) {
			retval = new String[1];
			retval[0]= id;
		} else {
			retval = id.split("-");
			if(retval.length > 2) {
				String[]cuttedRetval= new String[2];
				cuttedRetval[0] = retval[0];
				cuttedRetval[1] = retval[1];
				return cuttedRetval;
			}
		}
		
		return retval;
	}
}
