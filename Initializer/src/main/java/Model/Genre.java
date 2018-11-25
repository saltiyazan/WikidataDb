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
	@Column(name="idgenre")
	private int idgenre;
	
	@Column(name="wikidatagenreid")
	private String wdgid;
	
	@Column(name="name")
	private String name;
	
	public int getId(){
		return idgenre;
	}
	public String getWikiId(){
		return wdgid;
	}
	
	public void setWikiId(String wid){
		wdgid=wid;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String n){
		name =n;
	}
}