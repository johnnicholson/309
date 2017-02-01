package transactions;

import dao.RoomTypeDAO;
import hibernate.HibernateUtil;
import model.RoomType;

import org.springframework.http.HttpStatus;

import org.hibernate.Hibernate;

import java.util.List;

public class RoomTypeTransactions {

  public static class GetAllRoomTypes extends Transaction<List<RoomType>> {

    @Override
    public List<RoomType> action() {
      if (isAdmin()) {
        RoomTypeDAO typeDAO = HibernateUtil.getDAOFact().getRoomTypeDAO();
        List<RoomType> types = typeDAO.findAll();
        return types;
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
        return null;
      }
    }
  }


  public static class GetRoomType extends Transaction<RoomType> {
    private int typeId;

    public GetRoomType(int typeId) {
      this.typeId = typeId;
    }

    @Override
    public RoomType action() {
      RoomType r = null;
      if (isAdmin()) {
        r = HibernateUtil.getDAOFact().getRoomTypeDAO().findById(typeId);
        if (r != null) {
          Hibernate.initialize(r);
        } else {
          // Non admin entities don't need to know if that id exists
          if (isAdmin()) {
            responseCode = HttpStatus.NOT_FOUND;
          } 
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return r;
    }

  }

  //does the transaction still stay as an Integer type because we are pushing them to the database by their id?
  public static class PostRoomType extends Transaction<Integer> {
    private RoomType type;

    public PostRoomType(RoomType type) {
      this.type = type;
    }

    @Override
    public Integer action() {
      RoomTypeDAO typeDAO = HibernateUtil.getDAOFact().getRoomTypeDAO();
      if (null == typeDAO.findByRoomType(type.getRoomType())){
          //&& (prs.getRole() == Role.Student || isAdmin())) {
        typeDAO.makePersistent(type);
      } else {
        responseCode = HttpStatus.BAD_REQUEST;
        return null;
      }
      return type.getId();
    }

  }



  public static class PutRoomType extends Transaction<Integer> {
    private RoomType type;
    private Integer id;

    public PutRoomType(RoomType type, Integer id) {
      this.type = type;
      this.id = id;
    }

   @Override
   public Integer action() {
     return null;
    }
  }
}