
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTCompanyController;
import com.foodtrip.ftcontroller.ModelUtils;
import com.foodtrip.ftmodeldb.model.Company;
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
        return ModelUtils.toCompanyWS(new FTCompanyController().getCompany(id));
    }
    
    @POST
    public Long createCompany(CompanyWS company) {
    	Company newCompany = new FTCompanyController().createCompany(ModelUtils.toCompanyDB(company));
    	return newCompany.getId();
    }
}
