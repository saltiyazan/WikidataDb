package hu.bme.wikidata.Initializer;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

import Dao.MoviesDao;
import Model.Movie;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableJpaRepositories("Dao")
@SpringBootApplication(scanBasePackages={"Dao","Model"})
public class InitializerApplication implements CommandLineRunner{

	@Autowired
	@Qualifier("mRepo")
	MoviesDao mRepo;

	public static void main(String[] args) {
		SpringApplication.run(InitializerApplication.class, args);
		
    }
    public static void printResult(HashMap rs , int size) {

      /*for (String variable : (ArrayList<String>) ((HashMap) rs.get("result")).get("variables")) {
        System.out.print(String.format("%-"+size+"."+size+"s", variable ) + " | ");
      }*/
      System.out.print("\n");
      for (HashMap value : (ArrayList<HashMap>) ((HashMap) rs.get("result")).get("rows")) {
        System.out.print(value.get("itemLabel"));
        System.out.print(value.get("item"));

        /* for (String key : value.keySet()) {
         System.out.println(value.get(key));
         }*/
        /*for (String variable : (ArrayList<String>) ((HashMap) rs.get("result")).get("variables")) {
          System.out.println(value.get(variable));
         // System.out.print(String.format("%-"+size+"."+size+"s", value.get(variable)) + " | ");
        }*/
        System.out.print("\n");
      }
    }
	@Override
	public void run(String... args) throws Exception {
		
		try {
	        Endpoint sp = new Endpoint("https://query.wikidata.org/sparql", false);

	        String querySelect = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
	            "\n" +
	            "#Movies released in 2017\n" +
	            "SELECT DISTINCT ?item ?itemLabel ?genre ?genreLabel WHERE {\n" +
	            "  ?item wdt:P31 wd:Q11424.\n" +
	            "  ?item wdt:P577 ?pubdate.\n" +
	            "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],en\". }\n" +
	            "  FILTER(( ?pubdate >= \"2017-01-01T00:00:00Z\"^^xsd:dateTime) && (?pubdate <= \"2017-12-31T00:00:00Z\"^^xsd:dateTime))\n" +
	            "  OPTIONAL {FILTER NOT EXISTS {\n" +
	            "\n" +
	            " ?item wdt:P136 ?genre. }}\n" +
	            "}";

	        HashMap rs = sp.query(querySelect);
	       // printResult(rs,30);
	        Movie m = new Movie();
	        ArrayList<HashMap> hm =(ArrayList<HashMap>) ((HashMap) rs.get("result")).get("rows");
	        m.setTitle((String)(hm.get(0).get("itemLabel")));
	        m.setWikidataMovieID((String)(hm.get(0).get("item")));
	        m.setDone(false);
	        m.setGenreId(null);
	     //   ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	      //  MoviesDao mRepo = context.getBean(MoviesDao.class);
	        mRepo.AddOneMovie(m);

	    }catch(EndpointException eex) {
	        System.out.println(eex);
	        eex.printStackTrace();
		
	    }
	}
}
