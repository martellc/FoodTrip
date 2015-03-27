
package com.foodtrip.ftService.rs;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodtrip.ftcontroller.FTProductController;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.ProductWS;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/product")
public class FTServiceProduct {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public Response getProduct(@PathParam(value = "id") Long id) {
        try {
    		ProductWS p = new FTProductController().getProduct(id);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(p)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
    
    @Path("farmerproducts/{farmerId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public Response getFarmerProducts(@PathParam(value = "farmerId") Long farmerId) {
        
        try {
        	List<ProductWS> list = new FTProductController().getFarmersProducts(farmerId);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(list)
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
    public Response updateProduct(ProductWS product) {
    	try {
    		ProductWS p = new FTProductController().updateProduct(product);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(p)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
    }
}
