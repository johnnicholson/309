package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import dao.DAOFactory;
import dao.HibernateDAOFactory;
import model.ComponentType;
import model.Person;

public class HibernateUtil {
  // Eager initialization of singletons
  private static SessionFactory factory = null;
  private static DAOFactory daoFact = null;

  public static SessionFactory getFactory() {
    if (factory == null)
    	factory = initFactory();
    return factory;
  }

  public static DAOFactory getDAOFact() {
    if (daoFact == null)
    	daoFact = new HibernateDAOFactory();
    return daoFact;
  }
  
  public static void setDAOFactory(DAOFactory fact) {
	  if (daoFact == null)
		  daoFact = fact;
	  else
		  throw new IllegalArgumentException("The daofactory can only be set on startup");
  }
  public static void setFactory(SessionFactory fact) {
	  if (factory == null)
		  factory = fact;
	  else
		  throw new IllegalArgumentException("The factory can only be set on startup");
  }
  
  private static SessionFactory initFactory() {
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    configuration.addAnnotatedClass(Person.class);
    configuration.addAnnotatedClass(ComponentType.class);
    ServiceRegistry serviceRegistry =
        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    return configuration.buildSessionFactory(serviceRegistry);
  }

}
