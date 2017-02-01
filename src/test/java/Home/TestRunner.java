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
  public static DAOFactory fact;
  @BeforeClass
  public static void topSetup() {
    fact = mock(DAOFactory.class);
    HibernateUtil.setDAOFactory(fact);
  }
}
