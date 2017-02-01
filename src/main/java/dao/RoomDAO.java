package dao;

import hibernate.HibernateUtil;
import model.Room;

public class RoomDAO extends GenericHibernateDAO<Room> {

  public Room findByRoomNumber(String roomNumber) {
    return (Room) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from Room where roomNumber = :roomNumber").uniqueResult();
  }

}