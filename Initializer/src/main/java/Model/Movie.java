package Model;

import javax.persistence.*;


@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int Id;

	@Column(name = "title")
	private String title;

	@Column(name = "WikidataMovieId")
	private String WikidataMovieId;

	@Column(name = "GenreId")
	private String genreId;

	@Column(name = "done")
	private boolean done;

	public int getId(){
		return Id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		title=t;
	}

	public String getWikidataMovieId() {
		return WikidataMovieId;
	}

	public void setWikidataMovieID(String WDId) {
		WikidataMovieId=WDId;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String GID) {
		genreId=GID;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void setDone(boolean stat){
		done=stat;
	}
}
