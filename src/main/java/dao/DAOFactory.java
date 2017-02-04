package dao;

import org.apache.log4j.Logger;

public abstract class DAOFactory {
  static Logger lgr = Logger.getLogger(DAOFactory.class);

  public static DAOFactory instance(Class factory) {
    try {
      return (DAOFactory) factory.newInstance();
    } catch (Exception ex) {
      lgr.error(ex); 
      throw new IllegalArgumentException();
    }
  }

  public abstract PersonDAO getPersonDAO();

  
  public abstract ComponentTypeDAO getComponentTypeDAO();

  public abstract RoomDAO getRoomDAO();
  public abstract RoomTypeDAO getRoomTypeDAO();
  public abstract CourseDAO getCourseDAO();

  public abstract EquipmentDAO getEquipmentDAO();
  public abstract ComponentDAO getComponentDAO();
}
