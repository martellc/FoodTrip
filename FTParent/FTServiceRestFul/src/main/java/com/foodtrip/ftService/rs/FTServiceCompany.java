
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTCompanyController;
import com.foodtrip.ftmodelws.CompanyWS;

@Path("/company")
public class FTServiceCompany {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public CompanyWS getCompany(@PathParam(value = "id") Long id) {
        return new FTCompanyController().getCompany(id);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON })
    public CompanyWS updateCompany(CompanyWS company) {
    	CompanyWS updatedCompany = new FTCompanyController().updateCompany(company);
    	return updatedCompany;
    }
}
