package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect
public class Section {

	private static final int BCRYPT_ROUNDS = 12;

	public Section() {

	}

	public Section(String name, Person prof, Course course, Component comp, Date startTime, Date endTime) {
		super();
		this.name = name;
		this.professor = professor;
		this.course = course;
		this.comp = comp;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	private Integer id;
	private String name;
	private Person professor;
	private Course course;
	private Component comp;
	private Date startTime, endTime;

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

	@ManyToOne
	public Person getProfessor() {
		return professor;
	}

	public void setProfessor(Person professor) {
		this.professor = professor;
	}

	
	@ManyToOne
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@ManyToOne
	public Component getComponent() {
		return comp;
	}

	public void setComponent(Component comp) {
		this.comp = comp;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "HH:00")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "HH:00")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
