package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonAutoDetect
public class Term {

  public Term() {
	  
  }
  
  public Term(String name) {
	  super();
	  this.name = name;
  }
  
  private Integer id;
  private String name;
  private Integer isPublished; // published or not
  private List<Section> sections;
  

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
  

  public Integer getIsPublished() {
	    return isPublished;
  }
  
  public void setIsPublished(Integer isPublished) {
	    this.isPublished = isPublished;
  }

  @OneToMany(fetch = FetchType.EAGER, mappedBy="term")
  public List<Section> getSections() {
    return sections;
  }

  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  public void addSection(Section section) {
    sections.add(section);
  }
}
