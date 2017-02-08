package transactions;

import com.google.api.services.gmail.Gmail.Users.Drafts.Delete;
import dao.EquipmentDAO;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import hibernate.HibernateUtil;
import model.Equipment;

public class EquipmentTransactions {

  public static class GetEquipment extends Transaction<Equipment> {

    private int equipID;

    public GetEquipment(int equipID) {
      this.equipID = equipID;
    }

    @Override
    public Equipment action() {
      Equipment equipment = null;
      if (isStaff()) {
        equipment = HibernateUtil.getDAOFact().getEquipmentDAO().findById(equipID);
        if (equipment != null) {
          Hibernate.initialize(equipment);
        } else {
          responseCode = HttpStatus.NOT_FOUND;
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return equipment;
    }

  }

  public static class PostEquipment extends Transaction<Integer> {
    private Equipment equipment;

      public PostEquipment(Equipment equipment) {
        this.equipment = equipment;
      }

      @Override
    public Integer action() {
        EquipmentDAO equipmentDAO = HibernateUtil.getDAOFact().getEquipmentDAO();
        if (isAdmin()) {
          equipmentDAO.makePersistent(equipment);
        } else {
          responseCode = HttpStatus.UNAUTHORIZED;
          return null;
        }
        return equipment.getId();
      }
  }


  public static class GetEquipmentList extends Transaction<List<Equipment>> {
    @Override
    public List<Equipment> action() {
      List<Equipment> equipments = null;
      if (isStaff()) {
        equipments = HibernateUtil.getDAOFact().getEquipmentDAO().findAll();
        if (equipments != null) {
          Hibernate.initialize(equipments);
        } else {
          responseCode = HttpStatus.NOT_FOUND;
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return equipments;
    }
  }

  public static class PutEquipment extends Transaction<Integer> {
    private Equipment equipment;
    private Integer id;

    public PutEquipment(Equipment equipment, Integer id) {
      this.equipment = equipment;
      this.id = id;
    }


    @Override
    public Integer action() {
      EquipmentDAO equipmentDAO = HibernateUtil.getDAOFact().getEquipmentDAO();
      Equipment dbequipment = equipmentDAO.findById(id);
      if (isAdmin()) {
        BeanUtils.copyProperties(equipment, dbequipment, "id");
      } else {
        this.responseCode = HttpStatus.UNAUTHORIZED;
      }
      return null;
    }


  }

  public static class DeleteEquipment extends Transaction<Integer> {

    private int equipID;

    public DeleteEquipment(int equipID) {
      this.equipID = equipID;
    }

    @Override
    public Integer action() {
      Equipment equipment = null;
      EquipmentDAO equipDao = null;

      // Check permissions
      if (isAdmin()) {
        equipDao = HibernateUtil.getDAOFact().getEquipmentDAO();
        equipment = equipDao.findById(equipID);

        // Delete if found
        if (equipment != null) {
          equipDao.makeTransient(equipment);
        } else {
          responseCode = HttpStatus.NOT_FOUND;
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return null;
    }
  }
}
