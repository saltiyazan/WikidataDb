package hu.bme.wikidata.Initializer;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.cfg.annotations.QueryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bordercloud.sparql.Endpoint;
import com.bordercloud.sparql.EndpointException;

import Dao.GenreDao;
import Dao.MoviesDao;
import Model.Genre;
import Model.Movie;
import Sparql.QueryBuilder;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableJpaRepositories({"Dao","Model"})
@EntityScan("Model")
@ComponentScan({"Model","Dao"})
@SpringBootApplication//(scanBasePackages={"Dao"})
public class InitializerApplication implements CommandLineRunner{

	@Autowired
	@Qualifier("mRepo")
	MoviesDao mRepo;
	
	@Autowired
	@Qualifier("gRepo")
	GenreDao gRepo;

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
	       /* QueryBuilder genBuilder=new QueryBuilder();
	        genBuilder.selectAs.put("Q201658", "film_genre");
	        genBuilder.selectAs.put("noval", "film_genreLabel");
	        genBuilder.isA.put("film_genre", "Q201658");
	        String querySelectgens= genBuilder.BuildQueryStringEn();
	        HashMap rs = sp.query(querySelectgens);

	        Genre g = new Genre();
	        gRepo.deleteAll();
	        ArrayList<HashMap> hm =(ArrayList<HashMap>) ((HashMap) rs.get("result")).get("rows");
	        for(HashMap h:hm ){
	        	g = new Genre();
	        	g.setWikiId((String)(h.get("film_genre")));
	 	        g.setName((String)(h.get("film_genreLabel")));
	 	        gRepo.AddOneGenre(g);

	        } */

	        QueryBuilder builder=new QueryBuilder();
	        builder.selectAs.put("Q11424", "item");
	        builder.selectAs.put("noval1", "itemLabel");
	        builder.selectAs.put("P136", "genre");
	        builder.selectAs.put("noval2", "genreLabel");
	        
	        builder.isA.put("item", "Q11424");
	        builder.hasA.put("item","P577");
	        ArrayList<String> cond = new ArrayList<>();
	        cond.add(">= \"2017-01-01T00:00:00Z\"^^xsd:dateTime");
	        cond.add("<= \"2017-12-31T00:00:00Z\"^^xsd:dateTime");
	        builder.filter.put("P577",cond);
	        builder.hasNo.put("Q11424", "P136");
	        String querySelectMovies= builder.BuildQueryStringEn();
	        System.out.println(querySelectMovies);
	       
	        HashMap rs = sp.query(querySelectMovies);
	        printResult(rs,30);
	        Movie m = new Movie();
	        mRepo.deleteAll();
	        ArrayList<HashMap> hm =(ArrayList<HashMap>) ((HashMap) rs.get("result")).get("rows");
	        for(HashMap h:hm ){
	        	m = new Movie();
	        	m.setTitle((String)(h.get("itemLabel")));
	 	        m.setWikidataMovieID((String)(h.get("item")));
	 	        m.setDone(false);
	 	        m.setGenreId(null);
		        mRepo.AddOneMovie(m);

	        } 
	       
	    }catch(EndpointException eex) {
	        System.out.println(eex);
	        eex.printStackTrace();
		
	    }
	}
}
