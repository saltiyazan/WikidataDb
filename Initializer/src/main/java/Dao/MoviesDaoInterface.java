package Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Model.Movie;

@Repository(value="moviesRepo")
public interface MoviesDaoInterface extends CrudRepository<Movie, Long> {
}
