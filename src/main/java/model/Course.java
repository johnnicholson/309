package model;

import java.util.List;

public class Course {
  
  public Course() {
    
  }
  
  private Integer id;
  private String name;
  private Integer units;
  private List<Component> components;
  
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
