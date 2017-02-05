package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;

@Entity
@JsonAutoDetect
public class Component {

  public Component() {
    
  }
  
  private Integer id;
  private ComponentType componentType;
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
  @OneToOne
  public ComponentType getComponentType() {
    return componentType;
  }
  public void setComponentType(ComponentType componentType) {
    this.componentType = componentType;
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
