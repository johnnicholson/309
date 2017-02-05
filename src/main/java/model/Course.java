package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonAutoDetect
public class Course {
  
  public Course() {
    
  }
  
  private Integer id;
  private String name;
  private Integer units;
  private List<Component> components;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getUnits() {
    return units;
  }
  public void setUnits(Integer units) {
    this.units = units;
  }
  @OneToMany(fetch = FetchType.EAGER)
  public List<Component> getComponents() {
    return components;
  }
  public void setComponents(List<Component> components) {
    this.components = components;
  }


  public void addComponent(Component cmp) {
    components.add(cmp);
  }
}
