package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.crypto.bcrypt.BCrypt;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"roomNumber"})})
@JsonAutoDetect
public class Room {

  private static final int BCRYPT_ROUNDS = 12;

  public Room() {

  }

  public Room(int capacity, String roomNumber, String roomType) {
    super();
    this.capacity = capacity;
    this.roomNumber = roomNumber;
    this.roomType = roomType;
  }


  private Integer id;
  private Integer capacity;
  private String roomNumber;
  private String roomType;
  

  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
	    return id;
  }
  
  public void setId(int id) {
	    this.id = id;
  }
  
  @JsonProperty
  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  @JsonProperty
  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  @JsonProperty
  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
    result = prime * result + ((roomNumber == null) ? 0 : roomNumber.hashCode());
    result = prime * result + ((roomType == null) ? 0 : roomType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != Room.class) {
      return false;
    }
    Room other = (Room) obj;
    if (getRoomNumber() == null) {
      if (getRoomNumber() != null) {
        return false;
      }
    } else if (!getRoomNumber().equals(other.getRoomNumber())) {
      return false;
    }
    return true;
  }

}