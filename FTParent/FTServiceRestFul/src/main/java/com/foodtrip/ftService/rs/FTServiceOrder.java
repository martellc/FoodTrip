
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTOrderController;
import com.foodtrip.ftmodelws.OrderWS;

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
    public OrderWS getOrder(@PathParam(value = "id") Long id) {
        return new FTOrderController().getOrder(id);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    public OrderWS updateOrder(OrderWS order) {
    	OrderWS updatedOrder = new FTOrderController().updateOrder(order);
    	return updatedOrder;
    }
}
