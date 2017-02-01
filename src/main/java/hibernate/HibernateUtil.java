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
  private static SessionFactory factory = initFactory();
  private static DAOFactory daoFact = new HibernateDAOFactory();

  public static SessionFactory getFactory() {
    return factory;
  }

  public static DAOFactory getDAOFact() {
    return daoFact;
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
