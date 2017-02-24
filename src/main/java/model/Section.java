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

	public Section(String name, Person prof, Course course, Component comp, Date startTime, Date endTime, Room room) {
		super();
		this.name = name;
		this.professor = prof;
		this.course = course;
		this.comp = comp;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
	}

	private Integer id;
	private String name;
	private Person professor;
	private Course course;
	private Component comp;
	private Date startTime, endTime;
	private Room room;

	// Days of week
	// Other representations are possible but this is simple
//	private Boolean Sunday;
//	private Boolean Monday;
//	private Boolean Tuesday;
//	private Boolean Wednesday;
//	private Boolean Thursday;
//	private Boolean Friday;
//	private Boolean Saturday;



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


}
