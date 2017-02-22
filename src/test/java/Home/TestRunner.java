package Home;

import Course.ComponentTypeTest;
import Person.PersonTest;
import dao.DAOFactory;
import hibernate.HibernateUtil;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.mockito.Mockito.mock;

@RunWith(Suite.class)
@Suite.SuiteClasses({PersonTest.class, ComponentTypeTest.class})
public class TestRunner {
  private static DAOFactory fact;

  public static DAOFactory getMockFact() {
    if (fact == null) {
      fact = mock(DAOFactory.class);
      HibernateUtil.setDAOFactory(fact);
      System.out.println("HIT");
    }
    return fact;
  }

  public static void resetObjects() {
    fact = null;
  }
}
