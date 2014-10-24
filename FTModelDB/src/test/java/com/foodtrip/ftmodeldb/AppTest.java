//package com.foodtrip.ftmodeldb;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//import junit.framework.Test;
//import junit.framework.TestSuite;
//
//import org.junit.runner.RunWith;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
//import org.springframework.data.neo4j.config.Neo4jConfiguration;
//import org.springframework.data.neo4j.support.Neo4jTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.foodtrip.ftmodeldb.model.Movie;
//import com.foodtrip.ftmodeldb.model.Person;
//import com.foodtrip.ftmodeldb.model.Role;
//import com.foodtrip.ftmodeldb.repo.MovieRepository;
//import com.foodtrip.ftmodeldb.repo.PersonRepository;
//
//@Configuration
//@EnableNeo4jRepositories(basePackages="com.foodtrip.ftmodeldb.repo")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"applicationContext.xml"})
//public class AppTest extends Neo4jConfiguration
//{
//	/**
//	 * Create the test case
//	 *
//	 * @param testName name of the test case
//	 */
//	public AppTest( )
//	{
//		setBasePackage("com.foodtrip.ftmodeldb");
//	}
//
//	@Autowired
//    public Neo4jTemplate t;
//	
//	/**
//	 * @return the suite of tests being tested
//	 */
//	public static Test suite()
//	{
//		return new TestSuite( AppTest.class );
//	}
//
//	@org.junit.Test
//	public void testApp()
//	{
//		persistedMovieShouldBeRetrievableFromGraphDb();
//	}
//	
//	@Autowired MovieRepository movieRepository;
//	@Autowired PersonRepository personRepository;
//	
//	
//	@Bean
//    public GraphDatabaseService graphDatabaseService() {
//        return t.getGraphDatabaseService();
//    }
//	
//	@Transactional
//	public void persistedMovieShouldBeRetrievableFromGraphDb() {
//		GraphDatabaseService template = graphDatabaseService();
//		
//		Transaction tx = template.beginTx();
//		personRepository.deleteAll();
//		movieRepository.deleteAll();	
//		
//		Person tomHanks = new Person("Tom", "Hanks");
//		personRepository.save(tomHanks);
//		
//		Long personID = tomHanks.getId();
//		
////		Movie forrestGump = new Movie("Forrest Gump");
////		movieRepository.save(forrestGump);
////		
//		
//		//set a role relation
//		Role role = new Role(tomHanks, forrestGump, "gay");
//		Set<Role> roles = new HashSet<Role>();
//		roles.add(role);
//		//forrestGump.setRoles(roles);
//		tomHanks.setRoles(roles);
//		//movieRepository.save(forrestGump);
//		personRepository.save(tomHanks);
//
//		
//		Movie retrievedMovie = movieRepository.findOne(forrestGump.getId());
//		Iterator<Role> itRole = retrievedMovie.ruoli.iterator();
//		while (itRole.hasNext()) {
//			Role r = itRole.next();
//			System.out.println("name" + r.getActor().getName());
//			System.out.println("surname" + r.getActor().getSurname());
//		}
//		
//		Person person = personRepository.findOne(personID);
//		Iterator<Role> personRoles = person.getRoles().iterator();
//		while (personRoles.hasNext()) {
//			Role r = personRoles.next();
//			System.out.println("role" + r.getRole());
//			System.out.println("movie" + r.getMovie().getTitle());
//		}
//		
//		tx.close();
//		//assertNotNull(role);
//		//assertEquals("retrieved movie title matches", "Forrest Gump", retrievedMovie.getTitle());
//	}
//}
