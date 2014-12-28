
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTProductController;
import com.foodtrip.ftcontroller.FTProductController;
import com.foodtrip.ftcontroller.ModelUtils;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodelws.ProductWS;
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
        return ModelUtils.toProductWS(new FTProductController().getProduct(id));
    }
    
    @POST
    public Long createProduct(ProductWS product) {
    	Product p = new FTProductController().createProduct(ModelUtils.toProductDB(product));
    	return p.getId();
    }
}