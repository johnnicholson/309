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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**This class represents the Room data type. 
 * It contains an id which represents its place in the database. 
 * It's fields include capacity, roomNumber, and roomType. 
 * These can be modified through the get and set methods. 
 * We overwrote the hashCode method which creates a hashCode based on the instance variables.
 * We overwrote the equals method to check to see if two rooms have the same values.
 * @author salonee and ryan
 * @since 2017-02-08
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"roomNumber"})})
@JsonAutoDetect
public class Room {

  private static final int BCRYPT_ROUNDS = 12;

  public Room() {

  }

  public Room(int capacity, String roomNumber, RoomType roomType) {
    super();
    this.capacity = capacity;
    this.roomNumber = roomNumber;
    this.roomType = roomType;
  }


  private Integer id;
  private Integer capacity;
  private String roomNumber;
  private RoomType roomType;
  

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
  @OneToOne
  public RoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomType roomType) {
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