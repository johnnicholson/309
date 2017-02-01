package Person;

import org.apache.log4j.Logger;

import dao.ComponentTypeDAO;
import dao.DAOFactory;
import dao.GenericHibernateDAO;
import dao.HibernateDAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;

public class MockDAOFact extends DAOFactory {
	static Logger lgr = Logger.getLogger(MockDAOFact.class);
	PersonDAO prsDAO;
	ComponentTypeDAO ctDAO;

	public PersonDAO getPersonDAO() {
		return prsDAO;
	}

	public ComponentTypeDAO getComponentTypeDAO() {
		return ctDAO;
	}


}
