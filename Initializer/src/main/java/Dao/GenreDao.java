package Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import Model.Genre;

@ComponentScan({"Dao","Model"})
@Component(value="gRepo")
public class GenreDao {
	
	@Autowired
	@Qualifier("genreRepo")
	private GenreDaoInterface genreRepo ;
	
	public void AddOneGenre(Genre m){
		genreRepo.save(m);
	}
	
	public void deleteAll(){
		genreRepo.deleteAll();
	}
}
