package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonAutoDetect
public class Term {

  private static final int BCRYPT_ROUNDS = 12;
  
  public Term() {
	  
  }
  
  public Term(String name) {
	  super();
	  this.name = name;
  }
  
  private Integer id;
  private String name;
  private Integer status; // published or not
//  private Section listOfSections;
  

  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
	    return id;
  }
  
  public void setId(int id) {
	    this.id = id;
  }
  
  public String getName() {
	    return name;
  }
  
  public void setName(String name) {
	    this.name = name;
  }
  

  public Integer getStatus() {
	    return status;
  }
  
  public void setStatus(Integer status) {
	    this.status = status;
  }
}
