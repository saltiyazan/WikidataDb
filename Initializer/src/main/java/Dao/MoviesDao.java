package Dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import Model.Movie;

@ComponentScan({"Dao","Model"})
@Component(value="mRepo")
public class MoviesDao  {

	@Autowired
	@Qualifier("moviesRepo")
	private MoviesDaoInterface moviesRepo ;
	
	public void AddOneMovie(Movie m){
		moviesRepo.save(m);
	}
	
	public void deleteAll(){
		moviesRepo.deleteAll();
	}

}
