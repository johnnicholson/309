package Home;
import Person.PersonTest;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PersonTest.class})
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
