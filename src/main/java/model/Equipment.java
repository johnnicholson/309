package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect
public class Equipment {

  public Equipment() {
  }

  private Integer id;
  private String name;
  private String description;

  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
