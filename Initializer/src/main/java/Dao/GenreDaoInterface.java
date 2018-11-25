package Dao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Genre;

@ConfigurationProperties(prefix="spring.datasource")
@ComponentScan({"Dao","Model"})
@Repository(value="genreRepo")
public interface GenreDaoInterface extends CrudRepository<Genre,Long>{

}
