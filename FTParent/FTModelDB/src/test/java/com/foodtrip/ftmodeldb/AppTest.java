package com.foodtrip.ftmodeldb;

import java.util.HashSet;
import java.util.List;

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
import com.foodtrip.ftmodeldb.model.Product.ProductType;
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

		Company c1 = new Company("Company 1","1", "11111");
		Company c2 = new Company("Company 2","2", "22222");
		Company c3 = new Company("Company 3","3", "33333");
		Company c4 = new Company("Company 4","4", "44444");

		productRepository.save(pomodoro);
		farmRepository.save(aziendaCacca);
		companyRepository.save(c1);
		companyRepository.save(c2);
		companyRepository.save(c3);
		companyRepository.save(c4);

		//create first order
		Order order = new Order();
		order.setFarm(aziendaCacca);
		order.setSerialNumber("00000001");
		OrderProductRel op = new OrderProductRel(order,pomodoro);
		op.setAmount(100000d);
		op.setQuantity(100d);
		order.setOrderProductRel(op);
		orderRepository.save(order);

		//Long orderID = order.getId();

		//create second order		
		Order order2 = new Order();
		order2.setFarm(aziendaCacca);
		order2.setSerialNumber("00000002");
		OrderProductRel op2 = new OrderProductRel(order2,pomodoro);
		op2.setAmount(100000d);
		op2.setQuantity(100d);
		order2.setOrderProductRel(op2);
		orderRepository.save(order2);

		//Long order2ID = order2.getId();


		//start the steps
		sell(order,aziendaCacca,c1,100000d,100d);
		sell(order,c1,c2,50000d,50d);
		sell(order,c1,c3,50000d,50d);

		sell(order2,aziendaCacca,c1,10000d,100d);
		sell(order2,c1,c4,10000d,100d);

		
		Long startID = order.getFarm().getId();
		Long endID = c3.getId();
		List<Company> companiesPath = companyRepository.getCompaniesPath(startID, endID,order.getId());
		
		for(Company c : companiesPath) {
			System.out.println(c.getName());
		}
		
		tx.close();
	}

	private void sell(Order order, Company seller, Company buyer,Double quantity,Double amount) {
		CompanyToCompanyRel rel = new CompanyToCompanyRel();
		rel.setBuyer(buyer);
		rel.setSeller(seller);

		rel.setOriginalOrderID(order.getId());

		rel.setQuantity(quantity);
		rel.setAmount(amount);

		if(buyer.getCompanyToCompanyRel() == null) {
			buyer.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
		}
		buyer.getCompanyToCompanyRel().add(rel);

		companyRepository.save(buyer);
	}

	private Farm createFarm() {
		Farm f = new Farm("Azienda Cacca","234234234","asdfasdf234");

		City cittaDellaCacca = new City("Città della cacca");

		Address a = new Address();
		a.setStreet("Via puzzosa");
		a.setNumber("100");
		a.setZipCode("09876");
		a.setCity(cittaDellaCacca);
		f.setAddress(a);

		return f;
	}

	private Product createProduct() {
		Product p = new Product("Pomodoro",ProductType.VEGETABLE.getType());
		p.setHarvestDate(20141010);
		p.setSerialNumber("000000000000000000001");
		return p;
	}
}
