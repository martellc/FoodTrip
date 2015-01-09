
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTProductController;
import com.foodtrip.ftmodelws.ProductWS;

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
    public ProductWS getProduct(@PathParam(value = "id") Long id) {
        return new FTProductController().getProduct(id);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    public ProductWS updateProduct(ProductWS product) {
    	ProductWS p = new FTProductController().updateProduct(product);
    	return p;
    }
}
