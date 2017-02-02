package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@JsonAutoDetect
public class Component {

  public Component() {
    
  }
  
  private Integer id;
  private ComponentType typeID;
  private Integer workUnits;
  private Integer hours;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public ComponentType getTypeID() {
    return typeID;
  }
  public void setTypeID(ComponentType typeID) {
    this.typeID = typeID;
  }
  public Integer getWorkUnits() {
    return workUnits;
  }
  public void setWorkUnits(Integer workUnits) {
    this.workUnits = workUnits;
  }
  public Integer getHours() {
    return hours;
  }
  public void setHours(Integer hours) {
    this.hours = hours;
  }
  
}
