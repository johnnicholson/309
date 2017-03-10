package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonAutoDetect
public class Section {

	public Section() {

	}

	public Section(String name, Person prof, Course course, Component comp, Date startTime, Date endTime, Room room,
			Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday) {
		super();
		this.name = name;
		this.professor = prof;
		this.course = course;
		this.comp = comp;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		this.sunday = sunday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
	}

	private Integer id;
	private String name;
	private Person professor;
	private Course course;
	private Component comp;
	private Date startTime, endTime;
	private Room room;
	private Term term;
	// Days of week
	// Other representations are possible but this is simple
	private Boolean sunday;
	private Boolean monday;
	private Boolean tuesday;
	private Boolean wednesday;
	private Boolean thursday;
	private Boolean friday;
	private Boolean saturday;



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

	@ManyToOne
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}


	public Boolean getSunday() {
		return sunday;
	}

	public void setSunday(Boolean sunday) {
		this.sunday = sunday;
	}

	public Boolean getMonday() {
		return monday;
	}

	public void setMonday(Boolean monday) {
		this.monday = monday;
	}


	public Boolean getTuesday() {
		return tuesday;
	}


	public void setTuesday(Boolean tuesday) {
		this.tuesday = tuesday;
	}

	public Boolean getWednesday() {
		return wednesday;
	}

	public void setWednesday(Boolean wednesday) {
		this.wednesday = wednesday;
	}

	public Boolean getThursday() {
		return thursday;
	}

	public void setThursday(Boolean thursday) {
		this.thursday = thursday;
	}

	public Boolean getFriday() {
		return friday;
	}

	public void setFriday(Boolean friday) {
		this.friday = friday;
	}

	public Boolean getSaturday() {
		return saturday;
	}

	public void setSaturday(Boolean saturday) {
		this.saturday = saturday;
	}

	@ManyToOne
	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
