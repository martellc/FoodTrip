
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTCompanyController;
import com.foodtrip.ftmodelws.FarmWS;

@Path("/farm")
public class FTServiceFarm {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
//    @Path("/{id}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON })
//    public CompanyWS getCompany(@PathParam(value = "id") Long id) {
//        return new FTCompanyController().getCompany(id);
//    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    public FarmWS updateFarm(FarmWS farm) {
    	FarmWS updatedFarm = new FTCompanyController().updateFarm(farm);
    	return updatedFarm;
    }
}
