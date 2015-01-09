package com.foodtrip.ftmodeldb;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.model.Address;
import com.foodtrip.ftmodeldb.model.City;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.CompanyToCompanyRel;
import com.foodtrip.ftmodeldb.model.Farm;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.OrderProductRel;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.CityRepository;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.FarmRepository;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodeldb.repo.PersonRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;

@Configuration
@EnableNeo4jRepositories(basePackages="com.foodtrip.ftmodeldb.repo")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"applicationContext.xml"})
public class AppTest extends Neo4jConfiguration
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( )
	{
		setBasePackage("com.foodtrip.ftmodeldb");
	}

	@Autowired
	public Neo4jTemplate t;

	@Autowired PersonRepository personRepository;
	@Autowired CityRepository cityRepository;
	@Autowired OrderRepository orderRepository;
	@Autowired ProductRepository productRepository;
	@Autowired CompanyRepository companyRepository;
	@Autowired FarmRepository farmRepository;

	private Company c1;

	private Company c2;

	private Company c3;

	private Company c4;

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( AppTest.class );
	}

	@org.junit.Test
	public void testApp()
	{
		persistedMovieShouldBeRetrievableFromGraphDb();
	}


	@Bean
	public GraphDatabaseService graphDatabaseService() {
		return t.getGraphDatabaseService();
	}

	@Transactional
	public void persistedMovieShouldBeRetrievableFromGraphDb() {
		GraphDatabaseService template = graphDatabaseService();

		Transaction tx = template.beginTx();

		try {
			cityRepository.deleteAll();	
		}catch(Exception e) {

		}

		try {
			personRepository.deleteAll();
		}catch(Exception e) {

		}
		try {
			orderRepository.deleteAll();
		}catch(Exception e) {

		}
		try {
			companyRepository.deleteAll();
		}catch(Exception e) {

		}
		try {
			farmRepository.deleteAll();
		}catch(Exception e) {

		}
		try {
			productRepository.deleteAll();
		}catch(Exception e) {

		}

		Product pomodoro = createProduct();
		Farm aziendaCacca = createFarm();
		pomodoro.setFarm(aziendaCacca);

		c1 = new Company("Company 1","1", "11111");
		c2 = new Company("Company 2","2", "22222");
		c3 = new Company("Company 3","3", "33333");
		c4 = new Company("Company 4","4", "44444");

		productRepository.save(pomodoro);
		farmRepository.save(aziendaCacca);
		companyRepository.save(c1);
		companyRepository.save(c2);
		companyRepository.save(c3);
		companyRepository.save(c4);

		//create first order
		Order order = new Order();
		//order.setFarm(aziendaCacca);
		order.setSerialNumber("00000001");
		OrderProductRel op = new OrderProductRel(order,pomodoro);
		op.setAmount(100000d);
		op.setQuantity(100d);
		order.setOrderProductRel(op);
		orderRepository.save(order);

		c1.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
		c2.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
		c3.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
		c4.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
		
		//start the steps
		CompanyToCompanyRel rel1 = createStep(order,aziendaCacca,c1,100000d,100d);
		c1.getCompanyToCompanyRel().add(rel1);
		c1 =  companyRepository.save(c1);
		
		CompanyToCompanyRel rel2 = createStep(order,c1,c2,100000d,100d);
		c2.getCompanyToCompanyRel().add(rel2);
		c2 =  companyRepository.save(c2);
		
		CompanyToCompanyRel rel3 = createStep(order,c2,c3,100000d,100d);
		c3.getCompanyToCompanyRel().add(rel3);
		c3 =  companyRepository.save(c3);
		
		CompanyToCompanyRel rel4 = createStep(order,c3,c4,100000d,100d);
		c4.getCompanyToCompanyRel().add(rel4);
		c4 =  companyRepository.save(c4);
		
		order.getEndPoint().add(c4.getId());
		orderRepository.save(order);
		
		Long endID = c4.getId();
	
		
		
		Iterable<Company> companies = companyRepository.getCompaniesPath(endID,order.getId());
//		Iterator<EntityPath<Company, Company>> a = c.iterator();
//		while(a.hasNext()) {
//			EntityPath<Company, Company> e = a.next();
//			
//			
//			Iterator<Company> it = e.allPathEntities(Company.class).iterator();
//			while(it.hasNext()) {
//				System.out.println(it.next().getName());
//			}
//		}
		Iterator<Company> cI = companies.iterator();
		while(cI.hasNext()) {
			Company c = cI.next();
			System.out.println(c.getName());
		}
		
		tx.close();
	}

	private CompanyToCompanyRel createStep(Order order, Company seller, Company buyer,Double quantity,Double amount) {
		CompanyToCompanyRel rel = new CompanyToCompanyRel();
		rel.setEnd(buyer);
		rel.setStart(seller);
		
		rel.setAlt(0f);
		rel.setDate(new Date());
		rel.setLat(0f);
		rel.setLng(0f);
		rel.setOriginalOrderSerialNumber(order.getSerialNumber());
		rel.setOriginalOrderID(order.getId());

		rel.setQuantity(quantity);
		rel.setAmount(amount);

		return rel;
	}

	private Farm createFarm() {
		Farm f = new Farm("Azienda Cacca","234234234","asdfasdf234");

		City cittaDellaCacca = new City("Città della cacca",null);

		Address a = new Address();
		a.setStreetName("Via puzzosa");
		a.setStreetNumber("100");
		a.setZipCode("09876");
		a.setCity(cittaDellaCacca);
		//f.setAddress(a);

		return f;
	}

	private Product createProduct() {
		Product p = new Product("Pomodoro","Vegetable");
		p.setHarvestDate(20141010);
		p.setSerialNumber("000000000000000000001");
		return p;
	}
}
