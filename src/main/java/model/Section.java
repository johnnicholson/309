package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;


@Entity
@JsonAutoDetect
public class Section {

  private static final int BCRYPT_ROUNDS = 12;
  
  public Section() {
	  
  }
  
  public Section(String nameOfSect, Person prof, Course courseName, Component comp, Integer time) {
	  super();
	  this.nameOfSect = nameOfSect;
	  this.prof = prof;
	  this.courseName = courseName;
	  this.comp = comp;
	  this.time = time;
  }
  
  private Integer id;
  private String nameOfSect;
  private Person prof;
  private Course courseName;
  private Component comp;
  private Integer time;
  //private Integer status; // don't think we need this for section
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
  
  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  public String getNameSect() {
	    return nameOfSect;
  }
  
 
  public void setNameSect(String nameOfSect) {
	    this.nameOfSect = nameOfSect;
  }
  
  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  @OneToOne
  public Person getProf() {
	    return prof;
  }
  
 
  public void setProf(Person prof) {
	    this.prof = prof;
  }
  
  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ManyToOne //is this right?
  public Course getCourse() {
	    return courseName;
  }
  
 
  public void setCourse(Course courseName) {
	    this.courseName = courseName;
  }
  
  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  @OneToOne //is this correct?
  public Component getComponent() {
	    return comp;
  }
  
 
  public void setComponent(Component comp) {
	    this.comp = comp;
  }
  
  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getStartTime() {
	    return time;
  }
  
 
  public void setStartTime(Integer time) {
	    this.time = time;
  }
  
  
  
  
}
