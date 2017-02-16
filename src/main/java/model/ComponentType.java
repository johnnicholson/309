package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


/* This class is the Model for the Component Type object
 * 
 * Christiana Ushana & John Nicholson
 * Created on Feb 8 2017
 */
@Entity
@JsonAutoDetect
public class ComponentType {

	public ComponentType() {}
	
	private Integer id;
	private String name;
	private String description;
	
	/*
	 * This function returns the ID of a component type object
	 */
	@Id
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	/*
	 * This function sets the ID of a component type object
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/*
	 * This function returns the name of a component type object
	 */
	@JsonProperty
	public String getName() {
		return name;
	}
	/*
	 * This function sets the name of a component type object
	 */
	public void setName(String name) {
		this.name = name;
	}
	/*
	 * This function returns the description of a component type object
	 */
	@JsonProperty
	public String getDescription() {
		return description;
	}
	/*
	 * This function sets the description of a component type object
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
