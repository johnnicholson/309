package dao;

import hibernate.HibernateUtil;
import model.RoomType;

public class RoomTypeDAO extends GenericHibernateDAO<RoomType> {

  public RoomType findByRoomType(String type) {
    return (RoomType) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from RoomType where roomType = :type").setString("type",type).uniqueResult();
  }

}