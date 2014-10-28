//package com.foodtrip.ftmodeldb;
//
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Transaction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
//import org.springframework.data.neo4j.config.Neo4jConfiguration;
//import org.springframework.data.neo4j.core.GraphDatabase;
//import org.springframework.data.neo4j.support.GraphDatabaseFactory;
//
//import com.foodtrip.ftmodeldb.model.Person;
//import com.foodtrip.ftmodeldb.repo.MovieRepository;
//import com.foodtrip.ftmodeldb.repo.PersonRepository;
//
//@Configuration
//@EnableNeo4jRepositories(basePackages = "com.foodtrip.ftmodeldb")
//public class App extends Neo4jConfiguration  {
//
//    public App() throws ClassNotFoundException {
//        setBasePackage("com.foodtrip.ftmodeldb");
//    }
//
//    @Bean
//    GraphDatabaseService graphDatabaseService() {
//        return new GraphDatabaseFactory().newEmbeddedDatabase("/home/martellinic/mobile/accessingdataneo4j.db");
//    }
//
//    @Autowired
//    MovieRepository movieRepository;
//    PersonRepository personRepository;
//
//    @Autowired
//    GraphDatabase graphDatabase;
//
//    public void run(String... args) throws Exception {
//    	ConfigurableApplicationContext applicationContext =  
//	        	new ClassPathXmlApplicationContext( "/spring/FTModelDB.xml");
//
//	    PersonRepository personRepository = applicationContext.getBean(PersonRepository.class);
//	        
//    	Person greg = new Person("Greg","Pippo");
//        Person roy = new Person("Roy","Pluto");
//        Person craig = new Person("Craig","Ciao");
//        
//        System.out.println("Before linking up with Neo4j...");
//        for (Person person : new Person[]{greg, roy, craig}) {
//            System.out.println(person);
//        }
//
//        Transaction tx = graphDatabase.beginTx();
//        try {
//            personRepository.save(greg);
//            personRepository.save(roy);
//            personRepository.save(craig);
//            
//            tx.success();
//        } finally {
//            tx.close();
//        }
//
//        applicationContext.close();
//    }
//
//    public static void main(String[] args) throws Exception {
//    	   new App().run("");
//    }
//
//}