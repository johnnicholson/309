package dao;

import hibernate.HibernateUtil;
import org.apache.log4j.Logger;

public class HibernateDAOFactory extends DAOFactory {
	static Logger lgr = Logger.getLogger(HibernateDAOFactory.class);


	public PersonDAO getPersonDAO() {
		return (PersonDAO) instantiateDAO(PersonDAO.class);
	}


	public EquipmentDAO getEquipmentDAO() {
		return (EquipmentDAO) instantiateDAO(EquipmentDAO.class);
	}

	public ComponentDAO getComponentDAO() {
		return (ComponentDAO) instantiateDAO(ComponentDAO.class);
	}
  
  public RoomDAO getRoomDAO() {
	  return (RoomDAO) instantiateDAO(RoomDAO.class);
  }
  
  public RoomTypeDAO getRoomTypeDAO() {
	  return (RoomTypeDAO) instantiateDAO(RoomTypeDAO.class);
  }
  public CourseDAO getCourseDAO() {
    return (CourseDAO) instantiateDAO(CourseDAO.class);
  }
  public TermDAO getTermDAO() {
	  return ((TermDAO) instantiateDAO(TermDAO.class));
  }
  



	public ComponentTypeDAO getComponentTypeDAO() {
		return (ComponentTypeDAO) instantiateDAO(ComponentTypeDAO.class);
	}

	private GenericHibernateDAO instantiateDAO(Class daoClass) {
		try {
			GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
			dao.setSession(HibernateUtil.getFactory().getCurrentSession());
			return dao;
		} catch (Exception ex) {
			lgr.error(ex);
			throw new IllegalArgumentException();
		}
	}
}
