package Home;
import Course.ComponentTypeTest;
import Person.PersonTest;
import dao.DAOFactory;
import hibernate.HibernateUtil;

import static org.mockito.Mockito.mock;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PersonTest.class, ComponentTypeTest.class})
public class TestRunner {
  private static DAOFactory fact;

  public static DAOFactory getMockFact() {
    if (fact == null) {
      fact = mock(DAOFactory.class);
      HibernateUtil.setDAOFactory(fact); 
    }
    return fact;
  }

}
