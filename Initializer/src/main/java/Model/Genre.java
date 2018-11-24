package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="genre")
public class Genre {

	@Id
	@GeneratedValue
	@Column(name="idGenre")
	private int idGenre;
	
	@Column(name="WikidataGenreId")
	private String WikidataGenreId;
	
	@Column(name="name")
	private String name;
	
}