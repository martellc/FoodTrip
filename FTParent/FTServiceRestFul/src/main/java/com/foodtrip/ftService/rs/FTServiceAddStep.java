
package com.foodtrip.ftService.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foodtrip.ftcontroller.FTCompanyController;
import com.foodtrip.ftcontroller.FTOperationController;
import com.foodtrip.ftcontroller.ModelUtils;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodelws.CompanyWS;

@Path("/addStep")
public class FTServiceAddStep {
    
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    
    @POST
    public void addStep(Long orderID, CompanyWS company) {
    	new FTOperationController().addStep(orderID, ModelUtils.toCompanyDB(company));
    }
}
