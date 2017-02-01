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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"type"})})
@JsonAutoDetect
public class RoomType {

  private static final int BCRYPT_ROUNDS = 12;

  public RoomType() {

  }

  public RoomType(String type) {
    super();
    this.type = type;
  }


  private Integer id;
  private String type;
  

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
  public String getRoomType() {
    return type;
  }

  public void setRoomType(String type) {
    this.type = type;
  }



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    if (obj.getClass() != RoomType.class) {
      return false;
    }
    RoomType other = (RoomType) obj;
    if (getRoomType() == null) {
      if (getRoomType() != null) {
        return false;
      }
    } else if (!getRoomType().equals(other.getRoomType())) {
      return false;
    }
    return true;
  }

}