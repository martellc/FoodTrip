package com.foodtrip.ftcontroller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import com.foodtrip.ftmodeldb.model.Address;
import com.foodtrip.ftmodeldb.model.City;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.Farm;
import com.foodtrip.ftmodeldb.model.Notification;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.OrderProductRel;
import com.foodtrip.ftmodeldb.model.Person;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodelws.AddressWS;
import com.foodtrip.ftmodelws.CityWS;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.FarmWS;
import com.foodtrip.ftmodelws.NotificationWS;
import com.foodtrip.ftmodelws.OrderWS;
import com.foodtrip.ftmodelws.PersonWS;
import com.foodtrip.ftmodelws.ProductWS;

public class ModelUtils {

	public static Company toCompanyDB(CompanyWS companyWS) {
		if(companyWS == null) {
			return null;
		}
		
		Company ret = new Company(); 
		populateCompanyOrFarm(ret, companyWS);
		
		return ret;
	}

	private static void populateCompanyOrFarm(Company ret, CompanyWS companyWS) {
		ret.setId(companyWS.getId());
		ret.setAddress(toAddressDB(companyWS.getAddress()));
		ret.setOwner(toPersonDB(companyWS.getOwner()));
		ret.setCompanyID(companyWS.getNumber());
		if(companyWS.getEmployees()!= null) {
			Set<Person> employeeDB = new HashSet<Person>();
			for(PersonWS p : companyWS.getEmployees()) {
				employeeDB.add(toPersonDB(p));
			}
			ret.setEmployees(employeeDB);
		}
		ret.setDescription(companyWS.getDescription());
		ret.setCertifications(companyWS.getCertifications());
		ret.setFacebookID(companyWS.getFacebookID());
		ret.setFoundingDate(companyWS.getFoundingDate());
		ret.setGooglePlusID(companyWS.getGooglePlusID());
		ret.setEmail(companyWS.getEmail());
		
		if(ret.getAddress() != null) {
			ret.setCountry(ret.getAddress().getState());	
		}
		
		ret.setName(companyWS.getName());
		ret.setPresident(toPersonDB(companyWS.getPresident()));
		ret.setType(companyWS.getType());
		ret.setVatNumber(companyWS.getVatNumber());
		ret.setAlt(companyWS.getAlt());
		ret.setLng(companyWS.getLng());
		ret.setLat(companyWS.getLat());
		

		ret.setCompanyKey(companyWS.getCompanyKey());
		ret.setCreator(ModelUtils.toPersonDB(companyWS.getCreator()));
		ret.setCompanyTypeDescription(companyWS.getCompanyTypeDescription());
		ret.setCompanyDescription(companyWS.getCompanyDescription());
	}

	public static Person toPersonDB(PersonWS personWS) {
		if (personWS == null) {
			return null;
		}
		
		Person ret = new Person();
		ret.setId(personWS.getId());
		
		ret.setName(personWS.getName());
		ret.setSurname(personWS.getSurname());
		ret.setEmail(personWS.getEmail());
		ret.setFacebookAccoount(personWS.getFacebookID());
		ret.setGooglePlusAccount(personWS.getGooglePlusID());
		ret.setFiscalCode(personWS.getFiscalCode());
		ret.setAddress(toAddressDB(personWS.getAddress()));
		ret.setVerifiedUser(false);
		ret.setMobileNumber(personWS.getMobileNumber());
		ret.setBirthDate(personWS.getBirthDate());
		ret.setBirthPlace(personWS.getBirthPlace());
		ret.setGender(personWS.getGender());
		
		return ret;
	}

	public static Address toAddressDB(AddressWS addressWS) {
		if(addressWS == null) {
			return null;
		}
		
		Address ret = new Address();
		ret.setId(addressWS.getId());
		City city = toCityDB(addressWS.getCity());
		ret.setCity(city);
		ret.setCityName(city != null ? city.getName() : null);
		ret.setStreetNumber(addressWS.getStreetNumber());
		ret.setStreetName(addressWS.getStreetName());
		ret.setZipCode(addressWS.getZipCode());
		ret.setState(addressWS.getState());
		
		return ret;
	}

	public static City toCityDB(CityWS city) {
		if(city == null) {
			return null;
		}
		
		return new City(city.getName(),city.getId());
	}

	
	public static CompanyWS toCompanyWS(Company company) {
		if (company == null) {
			return null;
		}
		
		CompanyWS retWS = new CompanyWS();
		populateCompanyOrFarmWS(retWS, company);
		
		return retWS;
	}

	private static void populateCompanyOrFarmWS(CompanyWS retWS,Company company) {
		retWS.setAddress(toAddressWS(company.getAddress()));
		
		if(company.getEmployees() != null) {
			Set<PersonWS> employeeDB = new HashSet<PersonWS>();
			for(Person p : company.getEmployees()) {
				employeeDB.add(toPersonWS(p));
			}
			
			retWS.setEmployees(employeeDB);
		}
		retWS.setDescription(company.getDescription());
		retWS.setCertifications(company.getCertifications());
		retWS.setId(company.getId());
		retWS.setName(company.getName());
		retWS.setNumber(company.getCompanyID());
		retWS.setOwner(toPersonWS(company.getOwner()));
		retWS.setPresident(toPersonWS(company.getPresident()));
		retWS.setType(company.getType());
		retWS.setFarm(CompanyWS.CompanyType.FARM.getType().equals(company.getType()));
		retWS.setVatNumber(company.getVatNumber());
		retWS.setAlt(company.getAlt());
		retWS.setLng(company.getLng());
		retWS.setLat(company.getLat());
		retWS.setCreator(ModelUtils.toPersonWS(company.getCreator()));
		retWS.setCompanyTypeDescription(company.getCompanyTypeDescription());
		retWS.setCompanyDescription(company.getCompanyDescription());
		retWS.setFacebookID(company.getFacebookID());
		retWS.setFoundingDate(company.getFoundingDate());
		retWS.setGooglePlusID(company.getGooglePlusID());
		retWS.setEmail(company.getEmail());
		retWS.setCompanyKey(company.getCompanyKey());
	}

	private static AddressWS toAddressWS(Address address) {
		if (address == null) {
			return null;
		}
		AddressWS retWS = new AddressWS();
		
		String cityName = address.getCityName();
		CityWS city = new CityWS();
		if(cityName != null) {
			city.setName(cityName);
		}
		
		if (address.getCity() != null && address.getCity().getName() != null) {
			city.setName(address.getCity().getName());
		}
		retWS.setCity(city);
		
		retWS.setState(address.getState());
		retWS.setStreetName(address.getStreetName());
		retWS.setStreetNumber(address.getStreetNumber());
		retWS.setZipCode(address.getZipCode());
		
		return retWS;
	}

	private static PersonWS toPersonWS(Person p) {
		if(p == null) {
			return null;
		}
		
		PersonWS retWS = new PersonWS();
		
		retWS.setAddress(toAddressWS(p.getAddress()));
		retWS.setAge(p.getAge());
		retWS.setEmail(p.getEmail());
		retWS.setFacebookID(p.getFacebookAccoount());
		retWS.setFiscalCode(p.getFiscalCode());
		retWS.setGooglePlusID(p.getGooglePlusAccount());
		retWS.setId(p.getId());
		retWS.setMobileNumber(p.getMobileNumber());
		retWS.setName(p.getName());
		retWS.setSurname(p.getSurname());
		
		retWS.setBirthDate(p.getBirthDate());
		retWS.setBirthPlace(p.getBirthPlace());
		retWS.setGender(p.getGender());
		
		return retWS;
	}

	public static Product toProductDB(ProductWS pWS) {
		Product pDB = new Product();
		pDB.setBiological(pWS.isBiological());
		pDB.setBiodynamic(pWS.isBiodynamic());
		pDB.setCertifications(pWS.getCertifications());
		pDB.setFarm(toCompanyDB(pWS.getFarm()));
		pDB.setHarvestDate(dateToInt(new Date(pWS.getHarvestDate())));
		pDB.setIpm(pWS.isIpm());
		pDB.setName(pWS.getName());
		pDB.setOgm(pWS.isOgm());
		pDB.setSawingDate(dateToInt(new Date(pWS.getSawingDate())));
		pDB.setSerialNumber(pWS.getSerialNumber());
		pDB.setSustainable(pWS.isSustainable());
		pDB.setType(pWS.getType());
		pDB.setId(pWS.getId());
		pDB.setDescription(pWS.getDescription());
		
		pDB.setLat(pWS.getLat());
		pDB.setLng(pWS.getLng());
		
		return pDB;
	}

	public static ProductWS toProductWS(Product pDB) {
		ProductWS pWS = new ProductWS();
		pWS.setId(pDB.getId());
		pWS.setBiological(pDB.isBiological());
		pWS.setBiodynamic(pDB.isBiodynamic());
		pWS.setCertifications(pDB.getCertifications());
		pWS.setFarm(toCompanyWS(pDB.getFarm()));
		pWS.setHarvestDate(intToDate(pDB.getHarvestDate()).getTime());
		pWS.setIpm(pDB.isIpm());
		pWS.setName(pDB.getName());
		pWS.setOgm(pDB.isOgm());
		pWS.setSawingDate(intToDate(pDB.getSawingDate()).getTime());
		pWS.setSerialNumber(pDB.getSerialNumber());
		pWS.setSustainable(pDB.isSustainable());
		pWS.setType(pDB.getType());
		pWS.setAlt(pDB.getAlt());
		pWS.setLng(pDB.getLng());
		pWS.setLat(pDB.getLat());
		pWS.setDescription(pDB.getDescription());
		return pWS;
	}

	public static FarmWS toFarmWS(Farm farm) {
		FarmWS fWS = new FarmWS();
		populateCompanyOrFarmWS(fWS, farm);
		
		return fWS;
	}

	public static Farm toFarmDB(FarmWS farm) {
		Farm fDB = new Farm();
		populateCompanyOrFarm(fDB, farm);
		
		return fDB;
	}

	public static Order toOrderDB(OrderWS order) {
		Order oDB = new Order();
		
		oDB.setDate(order.getDate());
		oDB.setId(order.getId());
		
		oDB.setOrderProductRel(toOrderProductRel(oDB, toProductDB(order.getProduct()),order.getAmout(),order.getQuantity()));
		oDB.setSerialNumber(order.getSerialNumber());
		
		return oDB;
	}

	private static OrderProductRel toOrderProductRel(Order order, Product product,Double amount, Double quantity) {
		OrderProductRel rel = new OrderProductRel();
		rel.setProduct(product);
		rel.setOrder(order);
		rel.setAmount(amount);
		rel.setQuantity(quantity);
		return rel;
	}

	public static OrderWS toOrderWS(Order order) {
		OrderWS oWS = new OrderWS();
		
		oWS.setAmout(order.getOrderProductRel().getAmount());
		oWS.setDate(order.getDate());
		ProductWS p = toProductWS(order.getOrderProductRel().getProduct());
		oWS.setId(order.getId());
		oWS.setProduct(p);
		oWS.setQuantity(order.getOrderProductRel().getQuantity());
		oWS.setSerialNumber(order.getSerialNumber());
		
		return oWS;
	}
	
	public static int dateToInt(Date data){
		if (data==null) {
			return 0;
		}
		//Calendar c = Calendar.getInstance();
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		return c.get(Calendar.YEAR)*10000+(c.get(Calendar.MONTH)+1)*100+c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Date intToDate(int data){
		//Calendar c = Calendar.getInstance();
		Calendar c = new GregorianCalendar();
		c.set(data/10000,(data%10000)/100-1,data%100,0,0,0);
		c.set(Calendar.MILLISECOND,0);
		return c.getTime();
	}

	public static NotificationWS toNotificationWS(Notification n, Product p, Company c, Company producer) {
		NotificationWS nWS = new NotificationWS();
		nWS.setDate(n.getDate());
		nWS.setId(n.getId());
		nWS.setFoodTrip(n.getFoodTrip());
		nWS.setMessage(n.getMessage());
		nWS.setProduct(ModelUtils.toProductWS(p));
		nWS.setState(n.getState());
		nWS.setStepID(n.getStepID());
		nWS.setCompany(ModelUtils.toCompanyWS(c));
		nWS.setCompany(ModelUtils.toCompanyWS(producer));
		
		return nWS;
	}
}
