
package com.foodtrip.ftService.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodtrip.ftcontroller.FTCompanyController;
import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.NotificationWS;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/company")
public class FTServiceCompany {

	@GET 
	@Produces("text/plain")
	public String getIt() {
		return "Hi there!";
	}

	@Path("/types")
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public List<String> getCompanyTypes() {
		List<String> types = new ArrayList<String>();
		types.add(CompanyWS.CompanyType.FARM.getType());
		types.add(CompanyWS.CompanyType.DISTRIBUTION.getType());
		types.add(CompanyWS.CompanyType.INTERMEDIARY.getType());
		types.add(CompanyWS.CompanyType.COOP.getType());
		return types;
	}

	@Path("/notifications/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response getNotifications(@PathParam(value = "id") Long id) {		
		try {
			NotificationWS[]  ntfs = new FTCompanyController().getNotifications(id);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(ntfs)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}

	@Path("license/{id}/{uuid}")
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response checkLicense(@PathParam(value = "id") Long id,@PathParam(value = "uuid") Long uuid) {
			FTCompanyController controller = new FTCompanyController();
			
			//CHECK THE LICENSE
			if(!controller.isLicenseOK(id, uuid)) {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(FoodtripError.INVALID_LICENSE.getCode())
						.type(MediaType.APPLICATION_JSON)
						.build();
			} else {
				return Response.status(Status.ACCEPTED)
				.entity(new Boolean(true))
				.type(MediaType.APPLICATION_JSON)
				.build();
			}
	}

	@Path("companyKey/{companyKey}")
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response getCompanyBykey(@PathParam(value = "companyKey") String companyKey) {
		try {
			FTCompanyController controller = new FTCompanyController();
			CompanyWS company = controller.getCompanyByKey(companyKey);
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(company)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}
	
	@Path("/search/{vatOrName}")
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response searchCompany(@PathParam(value = "vatOrName")String vatOrName) {
		CompanyWS[] company = new FTCompanyController().searchCompany(vatOrName);
		return Response.status(Status.ACCEPTED)
				.type(MediaType.APPLICATION_JSON)
				.entity(company)
				.build();	
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON })
	public Response updateCompany(CompanyWS company) {
		try {
			CompanyWS updatedCompany = new FTCompanyController().updateCompany(company);		
			return Response.status(Status.ACCEPTED)
					.type(MediaType.APPLICATION_JSON)
					.entity(updatedCompany)
					.build();	
		} catch (FoodtripException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getBusinessCode())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}
}
