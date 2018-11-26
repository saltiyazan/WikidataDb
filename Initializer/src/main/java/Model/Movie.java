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

	@Column(name = "Wikidatamovieid")
	private String wkdmid;

	@Column(name = "genreid")
	private Genre wdgenreId;

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
		return wkdmid;
	}

	public void setWikidataMovieID(String WDId) {
		wkdmid=WDId;
	}

	public Genre getGenreId() {
		return wdgenreId;
	}

	public void setGenreId(Genre GID) {
		wdgenreId=new Genre();
		wdgenreId.setName(GID.getName());
		wdgenreId.setWikiId(GID.getWikiId());
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void setDone(boolean stat){
		done=stat;
	}
}
