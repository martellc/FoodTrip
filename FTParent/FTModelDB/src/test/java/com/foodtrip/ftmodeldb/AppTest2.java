package com.foodtrip.ftmodeldb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.kernel.Traversal;
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
import com.foodtrip.ftmodeldb.model.Farm;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.OrderProductRel;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.model.Step;
import com.foodtrip.ftmodeldb.repo.CityRepository;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodeldb.repo.PersonRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodeldb.repo.StepRepository;

@Configuration
@EnableNeo4jRepositories(basePackages="com.foodtrip.ftmodeldb.repo")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"applicationContext.xml"})
public class AppTest2 extends Neo4jConfiguration
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest2( )
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
	@Autowired StepRepository stepRepository;

	private Company c1;
	private Company c2;
	private Company c3;
	private Company c4;

	public static Test suite()
	{
		return new TestSuite( AppTest2.class );
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
			stepRepository.deleteAll();
		}catch(Exception e) {

		}
		try {
			productRepository.deleteAll();
		}catch(Exception e) {

		}

		//create companies (c1 is the farm
		c1 = new Company("Company 1","1", "11111");
		c2 = new Company("Company 2","2", "22222");
		c3 = new Company("Company 3","3", "33333");
		c4 = new Company("Company 4","4", "44444");

		companyRepository.save(c1);
		companyRepository.save(c2);
		companyRepository.save(c3);
		companyRepository.save(c4);

		//create tomato
		Product pomodoro = createProduct();
		pomodoro.setFarm(c1);
		productRepository.save(pomodoro);

		//create first order
		Order order = new Order();
		order.setSerialNumber("00000001");
		OrderProductRel op = new OrderProductRel(order,pomodoro);
		op.setAmount(100000d);
		op.setQuantity(100d);
		order.setOrderProductRel(op);
		orderRepository.save(order);

		//create first step for order
		Long orderID = order.getId();
		Step firstStep = new Step();
		firstStep.setQuantity(order.getOrderProductRel().getQuantity());
		firstStep.setAmount(order.getOrderProductRel().getAmount());
		firstStep.setLat(order.getOrderProductRel().getProduct().getLat());
		firstStep.setLng(order.getOrderProductRel().getProduct().getLng());
		firstStep.setAlt(order.getOrderProductRel().getProduct().getAlt());
		firstStep.setOrderID(orderID);
		firstStep.setCompanyID(c1.getId());
		stepRepository.save(firstStep);
		order.setStep(firstStep);
		orderRepository.save(order);

		//seconds step
		Step step1_0 = new Step();
		step1_0.setQuantity(50d);
		step1_0.setAmount(200000d);
		step1_0.setOrderID(orderID);
		step1_0.setCompanyID(c2.getId());
		stepRepository.save(step1_0);

		Step step1_1 = new Step();
		step1_1.setQuantity(50d);
		step1_1.setAmount(200000d);
		step1_1.setOrderID(orderID);
		step1_1.setCompanyID(c3.getId());
		stepRepository.save(step1_1);

		firstStep.getNextSteps().add(step1_1);
		firstStep.getNextSteps().add(step1_0);
		stepRepository.save(firstStep);

		Step step2 = new Step();
		step2.setQuantity(50d);
		step2.setAmount(300000d);
		step2.setOrderID(orderID);
		step2.setCompanyID(c4.getId());
		stepRepository.save(step2);

		step1_0.getNextSteps().add(step2);
		step1_1.getNextSteps().add(step2);
		stepRepository.save(step1_0);
		stepRepository.save(step1_1);

	
		//		Node a = (Node) firstStep;
		//		for ( Path position : template.traversalDescription()
		//		        .depthFirst()
		//		        .relationships( Rels.STEP )
		//		        .evaluator( Evaluators.toDepth( 5 ) )
		//		        .traverse(a))
		//		{
		//		    output += position + "\n";
		//		}




		Node a = template.getNodeById(firstStep.getId());
		Node b = template.getNodeById(step2.getId());
		TraversalDescription traversalDescription = Traversal.description()
				.breadthFirst()
				.relationships(Rels.STEP)
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(Evaluators.endNodeIs(Evaluation.INCLUDE_AND_CONTINUE,Evaluation.INCLUDE_AND_CONTINUE, b))
				.uniqueness(Uniqueness.RELATIONSHIP_GLOBAL );

		StepWS ws = new StepWS();
		ws.init(a, traversalDescription);
		ws.print();
		
		stepRepository.delete(step2);

		Iterable<Step> steps = stepRepository.getCurrentSteps(firstStep.getId());
		Iterator<Step> stepsIterator = steps.iterator();
		while(stepsIterator.hasNext()) {
			Step s = stepsIterator.next();
			System.out.println(s.getId());
		}

	}		
	//		Iterator<Company> cI = companies.iterator();
	//		while(cI.hasNext()) {
	//			Company c = cI.next();
	//			System.out.println(c.getName());
	//		}
	//		
	//		tx.close();
	//	}
	//
	//	private CompanyToCompanyRel createStep(Order order, Company seller, Company buyer,Double quantity,Double amount) {
	//		CompanyToCompanyRel rel = new CompanyToCompanyRel();
	//		rel.setEnd(buyer);
	//		rel.setStart(seller);
	//		
	//		rel.setAlt(0f);
	//		rel.setDate(new Date());
	//		rel.setLat(0f);
	//		rel.setLng(0f);
	//		rel.setOriginalOrderSerialNumber(order.getSerialNumber());
	//		rel.setOriginalOrderID(order.getId());
	//
	//		rel.setQuantity(quantity);
	//		rel.setAmount(amount);
	//
	//		return rel;
	//	}
	//

	private enum Rels implements RelationshipType
	{
		STEP
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

	private class StepWS {

		private Company node;
		private Map<Long,StepWS> children =  new HashMap<Long,StepWS>();
		
		public Map<Long, StepWS> getChildren() {
			return children;
		}

		public void setChildren(Map<Long, StepWS> children) {
			this.children = children;
		}

		public void print() {
			print(this);
		}

		private void print(StepWS stepWS) {
			System.out.println("Step:" + stepWS.getNode().getId());
			for(StepWS child : stepWS.getChildren().values()) {
				print(child);
			}
		}

		public Company getNode() {
			return node;
		}

		public void setNode(Company node) {
			this.node = node;
		}

		public void init (Node node, TraversalDescription path) {
			Step step = stepRepository.findOne(node.getId());
			this.node = companyRepository.findOne(step.getCompanyID());
			
			if(path == null) {
				return;
			}

			for (Path position : path.traverse(node)) {
				if(position.length() == 0) {
					continue;
				}
				
				System.out.println(position.toString());
				
				Node n = null;
				StepWS previous = this;
				
				Iterator<Node> it = position.nodes().iterator();
				while (it.hasNext()) {
					n = it.next();
					if (n.equals(node)) {
						continue;
					}
					//crea uno stepWS
					Step s = stepRepository.findOne(n.getId());
					StepWS child = new StepWS();
					
					child.setNode(companyRepository.findOne(s.getCompanyID()));
					
					previous.children.put(child.getNode().getId(), child);
					previous = child;
				}
			}
		}
	}
}
