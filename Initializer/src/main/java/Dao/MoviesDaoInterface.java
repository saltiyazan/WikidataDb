package Dao;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Movie;

@ConfigurationProperties(prefix="spring.datasource")
@ComponentScan({"Dao","Model"})
@Repository(value="moviesRepo")
public interface MoviesDaoInterface extends CrudRepository<Movie, Long> {
}
